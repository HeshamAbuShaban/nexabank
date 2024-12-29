package com.nexabank.network

import okhttp3.Interceptor
import okhttp3.Response
import com.nexabank.util.EncryptionUtil
import com.nexabank.util.TokenManager

class SecureHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // Add encrypted headers
        val encryptedToken = EncryptionUtil.encrypt(TokenManager.getToken() ?: "") // TODO: Replace "yourToken" dynamically
        val request = original.newBuilder()
            .header("Authorization", "Bearer $encryptedToken")
            .header("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}