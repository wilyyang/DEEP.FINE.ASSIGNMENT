package com.deepfine.assignment.domain.usecase.auth.validator

object AuthValidator {
    private val EMAIL_REGEX = Regex(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$"
    )
    private val PASSWORD_REGEX = Regex(
        "^(?=.{8,}$)" +
                "(?:(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)|" +
                "(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9])|" +
                "(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])|" +
                "(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]))" +
                ".*$"
    )

    fun isValidEmail(email: String) = EMAIL_REGEX.matches(email)
    fun isValidPassword(password: String) = PASSWORD_REGEX.matches(password)
}