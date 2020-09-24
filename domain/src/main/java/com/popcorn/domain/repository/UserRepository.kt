package com.popcorn.domain.repository

import com.popcorn.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserDetail(id: Int): Flow<User>
    suspend fun getUsers(): Flow<List<User>>
}
