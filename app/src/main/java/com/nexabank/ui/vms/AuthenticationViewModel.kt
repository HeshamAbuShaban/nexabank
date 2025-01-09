package com.nexabank.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.nexabank.R
import com.nexabank.core.AppSharedPreferences
import com.nexabank.databinding.FragmentLoginBinding
import com.nexabank.databinding.FragmentRegisterBinding
import com.nexabank.models.enums.Role
import com.nexabank.repository.AuthenticationRepository
import com.nexabank.ui.MainActivity
import com.nexabank.util.AlarmUtil
import com.nexabank.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: AuthenticationRepository,
    private val appSharedPreferences: AppSharedPreferences
) :
    ViewModel() {
    private lateinit var viewBinding: ViewBinding
    private lateinit var loginBinding: FragmentLoginBinding
    private lateinit var registerBinding: FragmentRegisterBinding
    private lateinit var navController: NavController

    // Initialize viewBinding and navController
    fun bind(viewBinding: ViewBinding, navController: NavController) {
        requireNotNull(viewBinding) { "viewBinding cannot be null" }
        this.viewBinding = viewBinding
        this.navController = navController
        initializeBindings(viewBinding)
    }

    /**
     * Initializes the login and register bindings based on the provided viewBinding.
     *
     * This function checks the type of the viewBinding and initializes the corresponding
     * binding instance (login or register). If the binding is not already initialized,
     * it attempts to bind it to the root view.
     *
     * @param viewBinding The ViewBinding instance to use for initialization.
     * @throws IllegalStateException if viewBinding is null.
     */
    private fun initializeBindings(viewBinding: ViewBinding) {
        when (viewBinding) {
            is FragmentLoginBinding -> {
                loginBinding = viewBinding
            }
            is FragmentRegisterBinding -> {
                registerBinding = viewBinding
            }
            else -> {
                // Handle cases where viewBinding is neither FragmentLoginBinding nor FragmentRegisterBinding
                // For example, log an error or throw an exception
                println("Warning: Unexpected viewBinding type: ${viewBinding.javaClass.name}")
            }
        }
    }

    fun register(
        username: String,
        password: String,
        email: String?,
        balance: Double?,
        role: Role,
        onRegisterSuccess: () -> Unit,
        onRegisterFailure: () -> Unit
    ) {
        viewModelScope.launch {
            val result = repository.register(username, password, email, balance, role)
            // Handle result
            if (result.isSuccess) {
                // Registration successful
                AlarmUtil.showSnackBar(
                    registerBinding.root,
                    result.getOrNull() ?: "Registration successful"
                )
                onRegisterSuccess()
            } else {
                // Registration failed
                result.exceptionOrNull()?.printStackTrace()
                AlarmUtil.showSnackBar(
                    registerBinding.root,
                    result.exceptionOrNull()?.message ?: "Registration failed"
                )
                onRegisterFailure()
            }
        }
    }

    fun login(
        username: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onLoginFailure: () -> Unit
    ) {
        viewModelScope.launch {
            TokenManager.removeToken()
            appSharedPreferences.removeUsername()
            appSharedPreferences.clearSharedPreferences()
            val result = repository.login(username, password)
            // Handle result
            if (result.isSuccess) {
                // Login successful
                val token = result.getOrNull()
                if (!token.isNullOrEmpty()) {
                    // Save token for future requests
                    TokenManager.saveToken(token)
                    appSharedPreferences.saveUsername(username)
                    AlarmUtil.showSnackBar(loginBinding.root, "Login successful")
                    onLoginSuccess()
                } else {
                    AlarmUtil.showSnackBar(loginBinding.root, "Token is empty")
                    onLoginFailure()
                }
            } else {
                // Login failed
                val exception = result.exceptionOrNull()
                AlarmUtil.showSnackBar(loginBinding.root, "Login failed: ${exception?.message}")
                onLoginFailure()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            val result = repository.logout()
            // Handle result
            if (result.isSuccess) {
                // Logout successful
                TokenManager.removeToken()
                AlarmUtil.showSnackBar(loginBinding.root, result.getOrNull() ?: "Logout successful")
                // TODO: Try > Navigation Component to login screen, Or Intent for a sure success
                navController.navigate(R.id.login_destination)
                navController.popBackStack()
                // finish the MainActivity to remove it from the back stack and prevent going back to it go to AuthActivity which is currently unAlive
                (loginBinding.root.context as MainActivity).finish()
            } else {
                result.exceptionOrNull()?.printStackTrace()
                AlarmUtil.showSnackBar(
                    loginBinding.root,
                    result.exceptionOrNull()?.message ?: "Logout failed"
                )
            }
        }

    }
}