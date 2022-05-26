package com.example.securepay.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.securepay.R
import com.example.securepay.databinding.FragmentPaymentHomeBinding
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject


class PaymentHomeFragment : Fragment(R.layout.fragment_payment_home),PaymentResultWithDataListener {

    private lateinit var binding: FragmentPaymentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentPaymentHomeBinding.bind(view)

        Checkout.preload(activity?.applicationContext )

        binding.payBtn.setOnClickListener {
            startPayment()
        }

    }
    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this@PaymentHomeFragment.requireActivity()
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
//            options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount","50000")//pass amount in currency subunits


            val prefill = JSONObject()
            prefill.put("email","gaurav.kumar@example.com")
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(s: String?, p1: PaymentData?) {
        binding.resultTv.text = "Successful payment ID: "+s.toString()
    }

    override fun onPaymentError(p0: Int, s: String?, p2: PaymentData?) {
        binding.resultTv.text = "UnSuccessful payment due to : "+s.toString()
    }


}