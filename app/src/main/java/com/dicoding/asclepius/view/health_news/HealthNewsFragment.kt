package com.dicoding.asclepius.view.health_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.asclepius.databinding.FragmentHealthNewsBinding

class HealthNewsFragment : Fragment() {
    private var _binding: FragmentHealthNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

}