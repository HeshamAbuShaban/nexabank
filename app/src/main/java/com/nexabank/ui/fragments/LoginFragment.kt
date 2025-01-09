package com.nexabank.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.nexabank.R
import com.nexabank.databinding.FragmentLoginBinding
import com.nexabank.models.dto.UserRequest
import com.nexabank.ui.MainActivity
import com.nexabank.ui.vms.AuthenticationViewModel
import com.nexabank.util.AlarmUtil
import com.nexabank.util.InputValidator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthenticationViewModel by viewModels()
    private lateinit var userRequest: UserRequest
    private lateinit var findNavController: NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
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
            loginButton.setOnClickListener {
//                if (checkInputs()) {
                preformLogin()
//                }
            }
            createAccount.setOnClickListener {
                findNavController.navigate(R.id.action_login_destination_to_register_destination)
            }
        }
    }


    private fun checkInputs(): Boolean {
        val inputValidator = InputValidator()
        with(binding) {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val isUsernameValid = inputValidator.isUsernameValid(username)
            val isPasswordValid = inputValidator.isPasswordValid(password)

            if (!isUsernameValid) {
                usernameEditText.error = "Username cannot be empty"
                AlarmUtil.showSnackBar(root, "Invalid username")
            }


            if (!isPasswordValid.isValid) {
                // Display the error message to the user
                passwordEditText.error = isPasswordValid.errorMessage
                AlarmUtil.showSnackBar(root, isPasswordValid.errorMessage!!)
            }

            userRequest = UserRequest(username, password)
            return isUsernameValid && isPasswordValid.isValid
        }
    }

    private fun preformLogin() {
        binding.loginButton.isEnabled = false
        binding.loginButton.text = getString(R.string.logging_in)
        // Temp Skipping real validation for development purposes
        with(binding) {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            viewModel.login(username = username, password = password, onLoginSuccess = {
                // Navigate to login screen
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }, onLoginFailure = {
                binding.loginButton.isEnabled = true
                binding.loginButton.text = getString(R.string.login)
            })
        }
    }
}