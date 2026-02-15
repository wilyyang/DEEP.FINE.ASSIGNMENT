package com.deepfine.assignment.domain.usecase.auth

import com.deepfine.assignment.core.common.di.DispatcherIO
import com.deepfine.assignment.domain.entity.user.UserInfo
import com.deepfine.assignment.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UseCaseLogin @Inject constructor(
    @param:DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): UserInfo? =
        withContext(dispatcher) {
            userRepository.login(email, password)
        }
}