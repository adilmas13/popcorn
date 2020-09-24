package com.popcorn.domain.usecase

import com.popcorn.domain.base.UseCase
import com.popcorn.domain.models.User
import com.popcorn.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersListUseCase @Inject constructor(private val repository: UserRepository) :
    UseCase<List<User>>() {

    override suspend fun makeRequest(): Flow<List<User>> {
        return repository.getUsers()
    }

    suspend fun getUsers(): Flow<List<User>> {
        return execute()
    }
}
