package com.nexabank.network.apis

import com.nexabank.models.dto.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {

    /*
    this is the raw body of the request looks like
    {
    "username": "hesham_adm",
    "password": "12345",
    "email": "mohip84299@ronete.com",
    "balance": 19540.0,
    "role": "ADMIN"
    }
    * */
    @POST("auth/register")
    suspend fun register(@Body request: UserRequest): Response<String>

    @POST("auth/login")
    suspend fun login(
        @Body request: UserRequest // e.g., username, password
    ): Response<String> // Token in response

    @POST("auth/logout")
    suspend fun logout(): Response<String>
}