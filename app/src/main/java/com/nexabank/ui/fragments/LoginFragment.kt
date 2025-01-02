package com.nexabank.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nexabank.databinding.FragmentLoginBinding
import com.nexabank.ui.MainActivity

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
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
        binding.loginButton.setOnClickListener {
            // we need to give context for the intent
            val context = requireContext()
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }
    }
}