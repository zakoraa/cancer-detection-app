package com.dicoding.asclepius.view.cancer_detection

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.asclepius.databinding.FragmentCancerDetectionBinding
import com.dicoding.asclepius.view.result.ResultActivity

class CancerDetectionFragment : Fragment() {

    private var _binding: FragmentCancerDetectionBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCancerDetectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
    }

    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
    }

    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
    }

    private fun moveToResult() {
        val intent = Intent(requireActivity(), ResultActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}