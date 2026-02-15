package com.deepfine.assignment.core.common.util

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {

    data object Empty : UiText()

    data class DynamicString(val value: String) : UiText()

    data class StringResource(
        @param:StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiText()

    fun asString(context: Context): String =
        when (this) {
            Empty -> ""
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args.toTypedArray())
        }
}