package com.nexabank.di

import com.nexabank.network.apis.AccountApi
import com.nexabank.network.ApiService
import com.nexabank.network.apis.AuthenticationApi
import com.nexabank.network.apis.CreditCardApi
import com.nexabank.network.apis.LoanApi
import com.nexabank.network.apis.TransactionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = ApiService.retrofit

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi = retrofit.create(AccountApi::class.java)

    @Provides
    @Singleton
    fun provideTransactionApi(retrofit: Retrofit): TransactionApi = retrofit.create(TransactionApi::class.java)

    @Provides
    @Singleton
    fun provideLoanApi(retrofit: Retrofit): LoanApi = retrofit.create(LoanApi::class.java)

    @Provides
    @Singleton
    fun provideCreditCardApi(retrofit: Retrofit): CreditCardApi = retrofit.create(CreditCardApi::class.java)

    @Provides
    @Singleton
    fun provideAuthenticationApi(retrofit: Retrofit): AuthenticationApi = retrofit.create(AuthenticationApi::class.java)

}