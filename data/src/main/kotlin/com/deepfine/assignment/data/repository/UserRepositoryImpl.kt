package com.deepfine.assignment.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.deepfine.assignment.core.common.error.DuplicateEmailException
import com.deepfine.assignment.data.datasource.database.user.UserDatabaseDao
import com.deepfine.assignment.data.datasource.database.user.model.UserInfoData
import com.deepfine.assignment.data.datasource.database.user.model.asDomain
import com.deepfine.assignment.domain.entity.user.UserInfo
import com.deepfine.assignment.domain.repository.UserRepository
import jakarta.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDatabaseDao: UserDatabaseDao
) : UserRepository {
    override suspend fun existsEmail(email: String): Boolean {
        return userDatabaseDao.existsByEmail(email)
    }

    override suspend fun login(email: String, password: String): UserInfo? {
        return userDatabaseDao.findByEmailAndPassword(email, password)?.asDomain()
    }

    override suspend fun register(email: String, name: String, password: String) {
        try {
            userDatabaseDao.insert(UserInfoData(email = email, name = name, password = password))
        } catch (t: Throwable) {
            if (t is SQLiteConstraintException) throw DuplicateEmailException(email)
            throw t
        }
    }
}