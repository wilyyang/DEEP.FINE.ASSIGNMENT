package com.deepfine.assignment.domain.usecase

import com.deepfine.assignment.domain.usecase.auth.validator.AuthValidator

import org.junit.Assert.assertTrue
import org.junit.Assert.*
import org.junit.Test


class AuthValidatorTest {

    // (Target) Action Result
    @Test
    fun `(email) valid 형식이 맞으면 true`() {
        assertTrue(AuthValidator.isValidEmail("android@deepfine.com"))
    }

    @Test
    fun `(email) invalid 도메인 없으면 false`() {
        assertFalse(AuthValidator.isValidEmail("android@deepfine"))
    }

    @Test
    fun `(password) valid 형식이 맞으면 true`() {
        assertTrue(AuthValidator.isValidPassword("Abcd1234!"))
    }

    @Test
    fun `(password) invalid 문자 형식 2가지만 만족하면 false`() {
        assertFalse(AuthValidator.isValidPassword("abcd1234"))
    }

    @Test
    fun `(password) invalid 8자 미만이면 false`() {
        assertFalse(AuthValidator.isValidPassword("abc123$"))
    }
}