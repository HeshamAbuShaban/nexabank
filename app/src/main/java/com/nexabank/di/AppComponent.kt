package com.nexabank.di

import android.content.Context
import com.nexabank.repository.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, GlideModule::class])
interface AppComponent {

    // Repository injections
    fun accountRepository(): AccountRepository
    fun transactionRepository(): TransactionRepository
    fun loanRepository(): LoanRepository
    fun creditCardRepository(): CreditCardRepository

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
