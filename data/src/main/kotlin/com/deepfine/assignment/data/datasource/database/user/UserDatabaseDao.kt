package com.deepfine.assignment.data.datasource.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deepfine.assignment.data.datasource.database.user.model.UserInfoData

@Dao
interface UserDatabaseDao {

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email LIMIT 1)")
    suspend fun existsByEmail(email: String): Boolean

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun findByEmailAndPassword(email: String, password: String): UserInfoData?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UserInfoData)
}