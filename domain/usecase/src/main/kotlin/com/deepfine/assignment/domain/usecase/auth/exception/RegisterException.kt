package com.deepfine.assignment.domain.usecase.auth.exception

sealed class RegisterException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause) {
    class DuplicateEmail(cause: Throwable? = null) : RegisterException(cause = cause)
    class StorageError(cause: Throwable? = null) : RegisterException(cause = cause)
}