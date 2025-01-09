package com.nexabank.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nexabank.R
import com.nexabank.core.AppSharedPreferences
import com.nexabank.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var appSharedPref: AppSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupUserData()
        setupListeners()
    }

    private fun setupUserData() {
        val username = appSharedPref.getUsername()
        binding.tvUserName.text = username
        binding.tvUserEmail.text = getString(R.string.to_be_implemented)
    }

    private fun setupListeners() {
        with(binding) {
            cardChangePassword.setOnClickListener {
                // Show bottom sheet dialog to change password screen
                /*val bottomSheet = ChangePasswordBottomSheet()
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)*/
            }
            cardChangeEmail.setOnClickListener {
                // Show bottom sheet dialog to change email screen
                /*val bottomSheet = ChangeEmailBottomSheet()
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)*/
            }
            cardContactUs.setOnClickListener {
                // Show bottom sheet dialog to contact us screen
                /*val bottomSheet = ContactUsBottomSheet()
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)*/
            }
            cardPrintInfo.setOnClickListener {
                // Show bottom sheet dialog to print info screen
                /*val bottomSheet = PrintInfoBottomSheet()
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)*/
            }
            cardLogout.setOnClickListener {
                // End user session and navigate to login screen
            }
        }
    }
}