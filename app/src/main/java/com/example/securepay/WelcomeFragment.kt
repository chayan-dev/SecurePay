package com.example.securepay

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.securepay.databinding.FragmentWelcomeBinding
import com.google.firebase.auth.FirebaseAuth


class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private lateinit var binding: FragmentWelcomeBinding

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentWelcomeBinding.bind(view)


        firebaseAuth = FirebaseAuth.getInstance()

        var currentUser = firebaseAuth.currentUser

        if (currentUser == null) {
            findNavController().navigate(R.id.action_welcomeFragment_to_phoneNumberFragment)
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
        }

        binding.idLogout.setOnClickListener {
            firebaseAuth.signOut()
            findNavController().navigate(R.id.action_welcomeFragment_to_phoneNumberFragment)
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
        }
    }

}