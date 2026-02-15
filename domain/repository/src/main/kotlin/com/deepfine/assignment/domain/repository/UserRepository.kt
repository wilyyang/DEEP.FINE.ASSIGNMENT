package com.deepfine.assignment.domain.repository

import com.deepfine.assignment.domain.entity.user.UserInfo

interface UserRepository {
    suspend fun existsEmail(email: String): Boolean
    suspend fun login(email: String, password: String): UserInfo?
    suspend fun register(email: String, name: String, password: String)
}