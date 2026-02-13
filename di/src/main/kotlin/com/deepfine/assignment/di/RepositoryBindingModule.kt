package com.deepfine.assignment.di

import com.deepfine.assignment.data.repository.UserRepositoryImpl
import com.deepfine.assignment.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBindingModule {

    @Binds
    @Singleton
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}