package com.deepfine.assignment.data.datasource.database.user.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deepfine.assignment.domain.entity.user.UserInfo

@Entity(tableName = "users")
data class UserInfoData(
    @PrimaryKey
    val email: String,
    val name: String,
    val password: String
)

fun UserInfoData.asDomain() = UserInfo(
    email = email,
    name = name
)