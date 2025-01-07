package com.nexabank.network

import okhttp3.Interceptor
import okhttp3.Response
import com.nexabank.util.EncryptionUtil
import com.nexabank.util.TokenManager

class SecureHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // Add encrypted headers
//        val encryptedToken = EncryptionUtil.encrypt(TokenManager.getToken() ?: "") // TODO: Replace "yourToken" dynamically
//        val tempToken = EncryptionUtil.encrypt("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXNoYW1fZmF2IiwiaWF0IjoxNzM2MTMzMzc2LCJleHAiOjE3MzYyMTk3NzZ9.WDishqTYqZELUAC6olmwRURuVDvlDkMaa-nDvgADCEE" ?: "") // TODO: Replace "yourToken" dynamically
        val tempToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZXNoYW1fYWRtIiwiaWF0IjoxNzM2MjA4OTIzLCJleHAiOjE3MzYyOTUzMjN9.DYBjCpqpJbIgJBZuAVMHMGVOecNgBH_aN7aNsQQkC0E"
        val request = original.newBuilder()
            .header("Authorization", "Bearer $tempToken")
            .header("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}