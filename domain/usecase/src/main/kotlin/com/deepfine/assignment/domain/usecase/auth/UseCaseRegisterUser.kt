package com.deepfine.assignment.domain.usecase.auth

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import com.deepfine.assignment.core.common.di.DispatcherIO
import com.deepfine.assignment.domain.repository.UserRepository
import com.deepfine.assignment.domain.usecase.auth.exception.RegisterException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UseCaseRegisterUser @Inject constructor(
    @param:DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, name: String, password: String) =
        withContext(dispatcher) {
            try {
                userRepository.register(email, name, password)
            } catch (e: SQLiteConstraintException) {
                throw RegisterException.DuplicateEmail(e)
            } catch (e: SQLiteException) {
                throw RegisterException.StorageError(e)
            }
        }
}