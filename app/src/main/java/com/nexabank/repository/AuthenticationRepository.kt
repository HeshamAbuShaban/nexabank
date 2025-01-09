package com.nexabank.repository

import android.util.Log
import com.nexabank.models.dto.UserRequest
import com.nexabank.models.enums.Role
import com.nexabank.network.apis.AuthenticationApi
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val api: AuthenticationApi) {

    suspend fun register(username: String, password: String, email: String?, balance: Double?, role: Role): Result<String> {
        return try {
            val userRequest = UserRequest(username, password, email, balance, role)
            val response = api.register(userRequest)
            if (response.isSuccessful) {
                Result.success(response.body() ?: "Registration successful")
            } else {
                Result.failure(Exception("Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(username: String, password: String): Result<String> {
        return try {
            val userRequest = UserRequest(username, password)
            val response = api.login(userRequest)
            if (response.isSuccessful) {
                val token = response.body() ?: throw Exception("Token is null or empty")
                Log.i("AuthRepo", "login: token: $token")
                return Result.success(token)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e("AuthRepo", "Login failed: $errorBody")
                return Result.failure(Exception("Login failed: $errorBody"))
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Login error: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<String> {
        return try {
            val response = api.logout()
            if (response.isSuccessful) {
                return Result.success(response.body() ?: "Logout successful")
            } else {
                return Result.failure(Exception("Logout failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}