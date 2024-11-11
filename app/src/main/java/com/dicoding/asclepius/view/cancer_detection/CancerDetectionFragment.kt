package com.dicoding.asclepius.view.cancer_detection

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicoding.asclepius.data.local.entity.CancerHistory
import com.dicoding.asclepius.databinding.FragmentCancerDetectionBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.view.ViewModelFactory
import com.dicoding.asclepius.view.cancer_history.CancerHistoryViewModel
import com.dicoding.asclepius.view.health_news.HealthNewsViewModel
import com.dicoding.asclepius.view.result.ResultActivity
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.task.vision.classifier.Classifications

class CancerDetectionFragment : Fragment() {

    private var _binding: FragmentCancerDetectionBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cropLauncher: ActivityResultLauncher<Intent>

    companion object {
        const val CANCER_HISTORY = "cancer_history"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCancerDetectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedUri = result.data?.data
                    selectedUri?.let { uri ->
                        startCrop(uri)
                    } ?: run {
                        Log.d("Photo Picker", "No media selected")
                    }
                }
            }

        cropLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val resultUri = UCrop.getOutput(result.data!!)
                    currentImageUri = resultUri
                    showImage()
                } else if (result.resultCode == UCrop.RESULT_ERROR) {
                    val cropError = UCrop.getError(result.data!!)
                    Log.e("Crop Error", "onActivityResult: $cropError")
                }
            }

        with(binding) {
            analyzeButton.setOnClickListener {
                currentImageUri?.let {
                    analyzeImage(it)
                } ?: showToast("No image selected")
            }

            galleryButton.setOnClickListener {
                startGallery()
            }
        }

        return root
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        galleryLauncher.launch(Intent.createChooser(intent, "Choose a Picture"))
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            binding.previewImageView.setImageURI(uri)
        }
    }

    private fun startCrop(uri: Uri) {
        val destinationUri =
            Uri.fromFile(requireContext().cacheDir.resolve("raflis-${System.currentTimeMillis()}.jpg"))
        val uCropIntent = UCrop.of(uri, destinationUri).getIntent(requireContext())

        cropLauncher.launch(uCropIntent)
    }

    private fun analyzeImage(image: Uri) {
        val imageHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    showToast(error)
                }

                override fun onResults(results: List<Classifications>?) {
                    results?.let {
                        val firstCategory = it[0].categories[0]
                        val label = firstCategory.label
                        val confidenceScore = (firstCategory.score * 100).toInt()

                        val resultString = "$label : $confidenceScore%"

                        val cancerHistory = CancerHistory(
                            image = image.toString(),
                            result = resultString,
                            confidenceScore = confidenceScore
                        )

                        lifecycleScope.launch(Dispatchers.IO) {
                            val factory: ViewModelFactory =
                                ViewModelFactory.getInstance(requireActivity())
                            val viewModel: CancerHistoryViewModel by viewModels { factory }

                            viewModel.insertCancerHistory(cancerHistory)

                            withContext(Dispatchers.Main) {
                                moveToResult(cancerHistory)
                            }
                        }
                    }
                }
            }
        )

        imageHelper.classifyStaticImage(image)

    }

    private fun moveToResult(cancerHistory: CancerHistory) {
        val intent = Intent(requireActivity(), ResultActivity::class.java)
        intent.putExtra(CANCER_HISTORY, cancerHistory)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
