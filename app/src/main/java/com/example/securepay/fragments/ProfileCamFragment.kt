package com.example.securepay.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.securepay.R
import com.example.securepay.databinding.FragmentProfileCamBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private lateinit var photoFile: File


class ProfileCamFragment : Fragment(R.layout.fragment_profile_cam) {

    private lateinit var binding: FragmentProfileCamBinding
    var bitmap:Bitmap?=null

    lateinit var sharedPrefCam:SharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentProfileCamBinding.bind(view)

        sharedPrefCam=this@ProfileCamFragment.requireContext().getSharedPreferences("spCam", Context.MODE_PRIVATE)

        binding.btnTakePicture.setOnClickListener {

            activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let { it1 ->
                ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .saveDir(it1)
                    .start()
            }
        }

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileCamFragment_to_docuCamFragment)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            binding.profileIv.setImageURI(uri)

//            val bitmapStream=MediaStore.Images.Media.getBitmap(requireContext().contentResolver,uri)

            //converting uri to bitmap
            bitmap= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, uri))
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }

            //bitmap to base64
            val outputStream=ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
            val imageStream=outputStream.toByteArray()
            val imageString=Base64.encodeToString(imageStream,Base64.DEFAULT)

//            binding.imageStreamTv.text=imageString

            Log.d("test","test")
            Log.d("api call",imageString)

            //storing in shared pref
            val editor=sharedPrefCam.edit()
            editor.apply{
                putString("pfCamByteArray", imageString)
                apply()
            }




        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this@ProfileCamFragment.requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@ProfileCamFragment.requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}