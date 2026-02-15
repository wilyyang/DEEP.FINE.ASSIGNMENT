package com.deepfine.assignment.core.common.error

class DuplicateEmailException(val email: String) :
    IllegalStateException("Email already registered: $email")
