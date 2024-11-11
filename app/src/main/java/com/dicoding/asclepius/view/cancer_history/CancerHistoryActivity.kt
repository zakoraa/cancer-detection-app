package com.dicoding.asclepius.view.cancer_history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.ResultStatus
import com.dicoding.asclepius.databinding.ActivityCancerHistoryBinding
import com.dicoding.asclepius.view.ViewModelFactory


class CancerHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCancerHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancerHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: CancerHistoryViewModel by viewModels {
            factory
        }

        binding.backImageButton.setOnClickListener {
            finish()
        }

        viewModel.getAllCancerHistories().observe(this) { result ->
            with(binding) {
                if (result != null) {
                    when (result) {
                        is ResultStatus.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }

                        is ResultStatus.Success -> {
                            progressBar.visibility = View.GONE
                            if (result.data.isEmpty()) {
                                noDataTextView.visibility = View.VISIBLE
                            }
                            val cancerHistoryAdapter = CancerHistoryAdapter(result.data)
                            with(binding) {
                                recyclerView.adapter = cancerHistoryAdapter
                                recyclerView.setHasFixedSize(true)
                                recyclerView.layoutManager =
                                    LinearLayoutManager(this@CancerHistoryActivity)
                            }
                        }

                        is ResultStatus.Error -> {
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@CancerHistoryActivity,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        }
    }
}