package com.dicoding.asclepius.view.result

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.asclepius.data.local.entity.CancerHistory
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.view.cancer_detection.CancerDetectionFragment

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cancerHistory = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(
                CancerDetectionFragment.CANCER_HISTORY,
                CancerHistory::class.java
            )
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(CancerDetectionFragment.CANCER_HISTORY)
        }

        with(binding) {
            cancerHistory?.let {
                resultText.text = it.result
                resultImage.setImageURI(Uri.parse(it.image))
            }

            backImageButton.setOnClickListener {
                finish()
            }
        }


    }

}