package com.example.flightticketingbooking.flightsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flightticketingbooking.databinding.FragmentFlightSearchResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightSearchResultFragment: Fragment() {
    private var _binding: FragmentFlightSearchResultBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlightSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}