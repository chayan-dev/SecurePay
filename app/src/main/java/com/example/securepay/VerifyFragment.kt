package com.example.securepay

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.securepay.databinding.FragmentVerifyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class VerifyFragment : Fragment(R.layout.fragment_verify) {

    private lateinit var binding: FragmentVerifyBinding

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentVerifyBinding.bind(view)

        firebaseAuth=FirebaseAuth.getInstance()

        lateinit var storedVerificationId:String

        setFragmentResultListener("requestKey") { requestKey, bundle ->

            storedVerificationId = bundle.getString("bundleKey").toString()
        }

        binding.verifyBtn.setOnClickListener{
            var otp=binding.idOtp.text.toString().trim()
            if(!otp.isEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this@VerifyFragment.requireContext(),"Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_verifyFragment_to_welcomeFragment)
//                    startActivity(Intent(applicationContext, Home::class.java))
//                    finish()
// ...
                } else {
// Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
                        Toast.makeText(this@VerifyFragment.requireContext(),"Invalid OTP",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

}