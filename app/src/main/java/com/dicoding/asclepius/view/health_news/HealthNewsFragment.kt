package com.dicoding.asclepius.view.health_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.ResultStatus
import com.dicoding.asclepius.databinding.FragmentHealthNewsBinding
import com.dicoding.asclepius.view.ViewModelFactory

class HealthNewsFragment : Fragment() {
    private var _binding: FragmentHealthNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: HealthNewsViewModel by viewModels {
            factory
        }

        viewModel.getAllHealthNews().observe(viewLifecycleOwner) { result ->
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
                            val healthNewsAdapter = HealthNewsAdapter(result.data)
                            with(binding) {
                                recyclerView.adapter = healthNewsAdapter
                                recyclerView.setHasFixedSize(true)
                                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                            }
                        }

                        is ResultStatus.Error -> {
                            progressBar.visibility = View.GONE
                            noDataTextView.visibility = View.VISIBLE
                            Toast.makeText(
                                requireContext(),
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        }
        return root

    }
}