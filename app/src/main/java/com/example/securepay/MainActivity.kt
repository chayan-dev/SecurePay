package com.example.securepay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.securepay.databinding.ActivityMainBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //for OTP resend
    private var forceResendingToken:PhoneAuthProvider.ForceResendingToken?=null

    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks?=null
    private var mVerificationId:String? =null
    private lateinit var firebaseAuth:FirebaseAuth

    private val TAG="MAIN_TAG"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()

        var currentUser = firebaseAuth.currentUser
        if(currentUser != null) {
            startActivity(Intent(applicationContext, Home::class.java))
            finish()
        }
        binding.loginBtn.setOnClickListener {
            login()
        }

        // Callback function for Phone Auth
        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, Home::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                mVerificationId = verificationId
                forceResendingToken = token

                var intent = Intent(applicationContext,Verify::class.java)
                intent.putExtra("storedVerificationId",mVerificationId)
                startActivity(intent)
            }
        }
}

    private fun login() {

        var number=binding.phoneNumber.text.toString().trim()

        if(!number.isEmpty()){
            number="+91"+number
            sendVerificationcode (number)
        }else{
            Toast.makeText(this,"Enter mobile number", Toast.LENGTH_SHORT).show()
        }

    }

    private fun sendVerificationcode(number: String) {
        val options = mCallBacks?.let {
            PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(it) // OnVerificationStateChangedCallbacks
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

}
