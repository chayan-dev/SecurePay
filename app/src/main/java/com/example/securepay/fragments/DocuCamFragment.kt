package com.example.securepay.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.securepay.R
import com.example.securepay.databinding.FragmentDocuCamBinding
import com.github.dhaval2404.imagepicker.ImagePicker

class DocuCamFragment : Fragment(R.layout.fragment_docu_cam) {

    private lateinit var binding: FragmentDocuCamBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentDocuCamBinding.bind(view)

        binding.button.setOnClickListener {
            activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let { it1 ->
                ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .saveDir(it1)
                    .start()
            }

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            binding.imageView.setImageURI(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this@DocuCamFragment.requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@DocuCamFragment.requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

}