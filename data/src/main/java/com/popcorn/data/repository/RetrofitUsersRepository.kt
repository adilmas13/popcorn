package com.popcorn.data.repository

import com.popcorn.data.repository.base.BaseRestApiRepository
import com.popcorn.data.retrofit.ApiService
import com.popcorn.domain.models.User
import com.popcorn.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrofitUsersRepository @Inject constructor(
    private val api: ApiService
) : UserRepository,
    BaseRestApiRepository() {

    override suspend fun getUsers(): Flow<List<User>> {
        return parseResult(api.getUsers()) { response -> response.users.map { it.toUser() } }
    }

    override suspend fun getUserDetail(id: Int): Flow<User> {
        return parseResult(api.getUserDetail(id)) { it.data.toUser() }
    }
}
