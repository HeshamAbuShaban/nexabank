package com.nexabank.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.nexabank.R
import com.nexabank.databinding.FragmentLoginBinding
import com.nexabank.models.enums.Role
import com.nexabank.repository.AuthenticationRepository
import com.nexabank.util.AlarmUtil
import com.nexabank.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val repository: AuthenticationRepository) : ViewModel() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    fun bind(binding: FragmentLoginBinding, navController: NavController) {
        this.binding = binding
        this.navController = navController
    }

    fun register(username: String, password: String, email: String?, balance: Double?, role: Role){
        viewModelScope.launch {
            val result = repository.register(username, password, email, balance, role)
            // Handle result
            if (result.isSuccess) {
                // Registration successful
                AlarmUtil.showSnackBar(binding.root, "Registration successful")
                // Navigate to login screen
                // TODO: navController.navigate(R.id.action_registerFragment_to_loginFragment)
                } else {
                // Registration failed
                val exception = result.exceptionOrNull()
                // Handle error
                exception?.printStackTrace()
                AlarmUtil.showSnackBar(binding.root, "Registration failed")
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = repository.login(username, password)
            // Handle result
            if (result.isSuccess) {
                // Login successful
                val token = result.getOrNull()
                // Save token for future requests
                TokenManager.saveToken(token.toString())
                AlarmUtil.showSnackBar(binding.root, "Login successful")
                // Navigate to main screen
                // TODO: navController.navigate(R.id.action_loginFragment_to_mainFragment)
            } else {
                // Login failed
                val exception = result.exceptionOrNull()
                // Handle error
                exception?.printStackTrace()
                // Clear token if login fails
                TokenManager.removeToken()
                AlarmUtil.showSnackBar(binding.root, "Login failed")
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
                AlarmUtil.showSnackBar(binding.root, "Logout successful")
                // Navigate to login screen
                // TODO: navController.navigate(R.id.action_mainFragment_to_loginFragment)
                } else {
                // Logout failed
                val exception = result.exceptionOrNull()
                // Handle error
                exception?.printStackTrace()
                AlarmUtil.showSnackBar(binding.root, "Logout failed")
            }
        }

    }
}