package com.example.securepay.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.securepay.R
import com.example.securepay.databinding.FragmentDocuCamBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.ByteArrayOutputStream
import java.util.*

class DocuCamFragment : Fragment(R.layout.fragment_docu_cam) {

    private lateinit var binding: FragmentDocuCamBinding

    var bitmap:Bitmap?=null


    val sharedPrefDocu=this@DocuCamFragment.requireContext().getSharedPreferences("spDocu",
        Context.MODE_PRIVATE)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentDocuCamBinding.bind(view)

        binding.DocuBtn.setOnClickListener {
            activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let { it1 ->
                ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .saveDir(it1)
                    .start()
            }

        }

        binding.DocuVerifyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_docuCamFragment_to_paymentHomeFragment)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            binding.DocuIv.setImageURI(uri)

            //converting uri to bitmap to byte array
            bitmap= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, uri))
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }
            val outputStream= ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
            val inputStream=outputStream.toByteArray()

            //storing in shared pref
            val editor=sharedPrefDocu.edit()
            editor.apply{
                putString("pfCamByteArray", Arrays.toString(inputStream))
            }


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this@DocuCamFragment.requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@DocuCamFragment.requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

}