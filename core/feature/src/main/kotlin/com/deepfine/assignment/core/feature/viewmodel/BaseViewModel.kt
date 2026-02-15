package com.deepfine.assignment.core.feature.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepfine.assignment.core.common.util.UiText
import com.deepfine.assignment.core.feature.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.CoroutineContext

interface ViewState
interface ViewEvent
interface ViewSideEffect


abstract class BaseViewModel<UiState : ViewState, Event : ViewEvent, Effect : ViewSideEffect> : ViewModel() {

    protected val scope = viewModelScope
    abstract fun setInitialState(): UiState
    abstract fun handleEvents(event: Event)
    private val initialState: UiState by lazy { setInitialState() }

    private val _uiState: MutableState<UiState> = mutableStateOf(initialState)
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow(extraBufferCapacity = 1)
    private val _effect: Channel<Effect> = Channel(Channel.BUFFERED)

    private val _overlayState: MutableState<OverlayState> = mutableStateOf(OverlayState.Init)
    private val _commonEvent: MutableSharedFlow<CommonEvent> = MutableSharedFlow(extraBufferCapacity = 1)
    private val _commonEffect: Channel<CommonEffect> = Channel(Channel.BUFFERED)

    val uiState: State<UiState> = _uiState
    val effect = _effect.receiveAsFlow().shareIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        replay = 0
    )
    val overlayState: State<OverlayState> = _overlayState
    val commonEffect = _commonEffect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        scope.launch {
            _event.collect {
                handleEvents(it)
            }
        }

        scope.launch {
            _commonEvent.collect {
                handleCommonEvents(it)
            }
        }
    }

    /**
     * 각 화면 별 State, Event, Effect
     */
    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = uiState.value.reducer()
        _uiState.value = newState
    }

    fun setEvent(event: Event) {
        scope.launch { _event.emit(event) }
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        scope.launch { _effect.send(effectValue) }
    }


    /**
     * 공통 State, Event, Effect
     */
    protected fun setOverlayState(state: OverlayState) {
        _overlayState.value = state
    }

    fun setCommonEvent(event: CommonEvent) {
        scope.launch { _commonEvent.emit(event) }
    }

    protected fun setCommonEffect(builder: () -> CommonEffect) {
        val effectValue = builder()
        scope.launch { _commonEffect.send(effectValue) }
    }

    open fun handleCommonEvents(event: CommonEvent) {
        when (event) {
            is CommonEvent.CloseEvent -> {
                setOverlayState(OverlayState.Close)
                setCommonEffect {
                    CommonEffect.Navigation.NavigateBack
                }
            }
            else -> {}
        }
    }

    // 공통 Coroutine
    fun launchWithInit(
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        timeoutMs: Long? = null,
        endOverlayState: OverlayState? = OverlayState.Idle,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return launchWithException(
            context = context,
            start = start,
            timeoutMs = timeoutMs,
            startOverlayState = OverlayState.Init,
            endOverlayState = endOverlayState,
            block = block
        )
    }

    fun launchWithLoading(
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        timeoutMs: Long? = null,
        endOverlayState: OverlayState? = OverlayState.Idle,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return launchWithException(
            context = context,
            start = start,
            timeoutMs = timeoutMs,
            startOverlayState = OverlayState.Loading,
            endOverlayState = endOverlayState,
            block = block
        )
    }

    fun launchWithException(
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        timeoutMs: Long? = null,
        startOverlayState: OverlayState? = null,
        endOverlayState: OverlayState? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return scope.launch(
            context + CoroutineExceptionHandler { _, t ->
                if (t is CancellationException) return@CoroutineExceptionHandler
                handleException(t)
            },
            start
        ) {
            startOverlayState?.let {
                withContext(Dispatchers.Main) {
                    _overlayState.value = startOverlayState
                }
            }

            var success = false
            try {
                if (timeoutMs != null) withTimeout(timeoutMs) { block() } else block()
                success = true
            } finally {
                if (success) {
                    endOverlayState?.let {
                        withContext(Dispatchers.Main) {
                            _overlayState.value = endOverlayState
                        }
                    }
                }
            }
        }.apply {
            invokeOnCompletion { cause ->
                cause?.let {
                    when (cause) {
                        is TimeoutCancellationException -> handleException(cause)
                        is CancellationException -> showExceptionResult(cause)
                    }
                }
            }
        }
    }

    /**
     * Exception 공통 처리
     */
    protected fun handleException(throwable: Throwable) {
        // 예외 중간 처리 (로깅)
        showExceptionResult(throwable)
    }

    protected open fun showExceptionResult(
        throwable: Throwable,
        defaultConfirm: () -> Unit = { setCommonEvent(CommonEvent.CloseEvent) },
        defaultDismiss: () -> Unit = { setOverlayState(OverlayState.Idle) }
    ) {
        scope.launch(Dispatchers.Main.immediate) {
            _overlayState.value = when (throwable) {
                is TimeoutCancellationException -> OverlayState.CommonDialog(
                    message = UiText.StringResource(R.string.dialog_error_default),
                    dismissText = null,
                    onConfirm = defaultConfirm
                )

                is CancellationException -> {
                    if (_overlayState.value is OverlayState.Loading) {
                        OverlayState.Idle
                    } else {
                        _overlayState.value
                    }
                }

                else -> OverlayState.CommonDialog(
                    message = UiText.StringResource(R.string.dialog_error_default),
                    dismissText = null,
                    onConfirm = defaultConfirm
                )
            }
        }
    }
}