package com.nexabank.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.nexabank.R
import com.nexabank.databinding.FragmentRegisterBinding
import com.nexabank.models.enums.Role
import com.nexabank.ui.vms.AuthenticationViewModel
import com.nexabank.util.AlarmUtil
import com.nexabank.util.InputValidator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthenticationViewModel by viewModels()
    private lateinit var findNavController: NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        findNavController = findNavController()
        viewModel.bind(binding, findNavController)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            registerButton.setOnClickListener {
//                if (!checkInputs()) {
                preformRegister()
//                }
            }
            haveAccount.setOnClickListener {
                findNavController.navigate(R.id.action_register_destination_to_login_destination)
            }
        }
    }


    private fun checkInputs(): Boolean {
        val inputValidator = InputValidator()
        with(binding) {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val balance: String = balanceEditText.text.toString()
            val email = emailEditText.text.toString()

            val isEmailValid = inputValidator.isEmailValid(email)
            val isBalanceValid = inputValidator.isBalanceValid(balance)

            val isUsernameValid = inputValidator.isUsernameValid(username)
            val isPasswordValid = inputValidator.isPasswordValid(password)

            if (!isUsernameValid) {
                usernameEditText.error = "Username cannot be empty"
                AlarmUtil.showSnackBar(root, "Invalid username")
            }

            if (!isEmailValid) {
                emailEditText.error = "Invalid email address"
                AlarmUtil.showSnackBar(root, "Invalid email address")
            }

            if (!isBalanceValid) {
                balanceEditText.error = "Invalid balance"
                AlarmUtil.showSnackBar(root, "Invalid balance")
            }

            if (!isPasswordValid.isValid) {
                // Display the error message to the user
                passwordEditText.error = isPasswordValid.errorMessage
                AlarmUtil.showSnackBar(root, isPasswordValid.errorMessage!!)
            }

            return isUsernameValid && isPasswordValid.isValid
        }
    }

    private fun preformRegister() {
        binding.registerButton.isEnabled = false
        binding.registerButton.text = getString(R.string.registering)
        // Temp Skipping real validation for development purposes
        with(binding) {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val balance: String = balanceEditText.text.toString()
            val email = emailEditText.text.toString()

            viewModel.register(
                username = username,
                password = password,
                email = email,
                balance = balance.toDouble(),
                role = Role.USER,
                onRegisterSuccess = {
                    // Navigate to login screen
                    findNavController.navigate(R.id.action_register_destination_to_login_destination)
                    binding.registerButton.isEnabled = true
                    binding.registerButton.text = getString(R.string.register)
                },
                onRegisterFailure = {
                    binding.registerButton.isEnabled = true
                    binding.registerButton.text = getString(R.string.register)
                })

        }
    }
}