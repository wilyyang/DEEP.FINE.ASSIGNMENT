package com.deepfine.assignment.data.datasource.database.user

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deepfine.assignment.data.datasource.database.user.model.UserInfoData

@Database(
    version = 1,
    exportSchema = false,
    entities = [UserInfoData::class]
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDatabaseDao(): UserDatabaseDao
}