package com.example.securepay.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.securepay.R
import com.example.securepay.databinding.FragmentProfileCamBinding
import java.io.File

private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private lateinit var photoFile: File

class ProfileCamFragment : Fragment(R.layout.fragment_profile_cam) {

    private lateinit var binding: FragmentProfileCamBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentProfileCamBinding.bind(view)

        binding.btnTakePicture.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            // This DOESN'T work for API >= 24 (starting 2016)
            // takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)

            val fileProvider =
                FileProvider.getUriForFile(this@ProfileCamFragment.requireContext(), "com.example.securepay.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            if (takePictureIntent.resolveActivity(this@ProfileCamFragment.requireContext().packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            } else {
                Toast.makeText(this@ProfileCamFragment.requireContext(), "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        }

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileCamFragment_to_docuCamFragment)
        }

    }

    private fun getPhotoFile(fileName: String): File {
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        val storageDirectory = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            val takenImage = data?.extras?.get("data") as Bitmap
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            binding.imageView.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}