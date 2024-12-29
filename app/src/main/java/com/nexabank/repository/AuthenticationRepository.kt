package com.nexabank.repository

import com.nexabank.models.dto.UserRequest
import com.nexabank.models.enums.Role
import com.nexabank.network.apis.AuthenticationApi
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val api: AuthenticationApi) {

    suspend fun register(username: String, password: String, email: String?, balance: Double?, role: Role): Result<Unit> {
        return try {
            val userRequest = UserRequest(username, password, email, balance, role)
            val response = api.register(userRequest)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(username: String, password: String): Result<Any> {
        return try {
            val userRequest = UserRequest(username, password)
            val response = api.login(userRequest)
            if (response.isSuccessful) {
                return Result.success(response.body() ?: "")
            } else {
                return Result.failure(Exception("Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<Unit> {
        return try {
            val response = api.logout()
            if (response.isSuccessful) {
                return Result.success(Unit)
            } else {
                return Result.failure(Exception("Logout failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}