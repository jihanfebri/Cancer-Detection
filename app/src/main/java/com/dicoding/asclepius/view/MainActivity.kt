package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.view.record.RecordActivity
import com.dicoding.asclepius.view.result.ResultActivity
import com.yalantis.ucrop.UCrop
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                moveToCrop(uri)
            } else {
                showToast(getString(R.string.failed_to_pick_image))
            }
        }

    private val cropActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                currentImageUri = UCrop.getOutput(data)
                showImage()
            }
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            result.data?.let { data ->
                val errorMessage = UCrop.getError(data)?.message.toString()
                showToast(errorMessage)
                Log.e(TAG, errorMessage)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        updateButtonStatus()

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { analyzeImage() }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
        updateButtonStatus()
    }

    private fun analyzeImage() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, currentImageUri.toString())
        startActivity(intent)
    }

    private fun moveToCrop(uri: Uri) {
        val destinationUri = Uri.fromFile(File(cacheDir, "${System.currentTimeMillis()}.jpg"))
        val uCrop = UCrop.of(uri, destinationUri)
        val uCropIntent = uCrop.getIntent(this)
        cropActivityLauncher.launch(uCropIntent)
    }

    private fun updateButtonStatus() {
        binding.analyzeButton.isEnabled = currentImageUri != null
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_history -> {
                val intent = Intent(this, RecordActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
