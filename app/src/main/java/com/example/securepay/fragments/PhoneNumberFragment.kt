package com.example.securepay.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.securepay.R
import com.example.securepay.databinding.FragmentPhoneNumberBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class PhoneNumberFragment : Fragment(R.layout.fragment_phone_number) {

    private lateinit var binding: FragmentPhoneNumberBinding

    //for OTP resend
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken?=null

    private lateinit var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var mVerificationId:String? =null
    private lateinit var firebaseAuth: FirebaseAuth

    private val TAG="MAIN_TAG"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentPhoneNumberBinding.bind(view)

        firebaseAuth=FirebaseAuth.getInstance()

        var currentUser = firebaseAuth.currentUser
        if(currentUser != null) {
            findNavController().navigate(R.id.action_phoneNumberFragment_to_welcomeFragment)

        }
        binding.loginBtn.setOnClickListener {
            login()
        }

        // Callback function for Phone Auth
        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                findNavController().navigate(R.id.action_phoneNumberFragment_to_welcomeFragment)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@PhoneNumberFragment.requireContext(), "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                mVerificationId = verificationId
                forceResendingToken = token

                var verificationId=mVerificationId

//                var intent = Intent(applicationContext,Verify::class.java)
                setFragmentResult("requestKey", bundleOf("bundleKey" to verificationId))
//                intent.putExtra("storedVerificationId",mVerificationId)
//                startActivity(intent)
                findNavController().navigate(R.id.action_phoneNumberFragment_to_verifyFragment)
            }
        }
    }

    private fun login() {

        var number=binding.phoneNumber.text.toString().trim()

        if(!number.isEmpty()){
            number="+91"+number
            sendVerificationcode (number)
        }else{
            Toast.makeText(this@PhoneNumberFragment.requireContext(),"Enter mobile number", Toast.LENGTH_SHORT).show()
        }

    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(mCallBacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


}