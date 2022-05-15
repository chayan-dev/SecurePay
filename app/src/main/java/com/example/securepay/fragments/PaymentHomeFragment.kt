package com.example.securepay.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.securepay.R
import com.example.securepay.databinding.FragmentPaymentHomeBinding


class PaymentHomeFragment : Fragment(R.layout.fragment_payment_home) {

    private lateinit var binding: FragmentPaymentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentPaymentHomeBinding.bind(view)

    }
}