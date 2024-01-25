package com.example.flightticketingbooking.airportcity.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flightticketingbooking.SessionManager
import com.example.flightticketingbooking.airportcity.adapter.CityAdapter
import com.example.flightticketingbooking.airportcity.adapter.CityClickListeners
import com.example.flightticketingbooking.airportcity.model.Data
import com.example.flightticketingbooking.airportcity.viewModel.AirportCityViewModel
import com.example.flightticketingbooking.dashboard.viewModel.DashboardViewModel
import com.example.flightticketingbooking.databinding.FragmentAirportCityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AirportCityFragment : Fragment(), CityClickListeners {
    private var _binding: FragmentAirportCityBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManger: SessionManager
    private var mkeyWords: String = "A"
    var mCityType: Int = 0
    lateinit var shareViewModel: DashboardViewModel
    private val FROM_CITY = 0
    private val TO_CITY = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAirportCityBinding.inflate(inflater, container, false)
        val args = AirportCityFragmentArgs.fromBundle(requireArguments())
        mCityType = args.cityType
        sessionManger = SessionManager(requireActivity())

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shareViewModel = ViewModelProvider(requireActivity())[DashboardViewModel::class.java]
        val viewModel: AirportCityViewModel by viewModels()
        (activity as AppCompatActivity).supportActionBar?.show()
        viewModel.getAirportCity("Bearer " + sessionManger.fetchAuthToken()!!, mkeyWords)

        /**
         * TextWatcher Listener
         */
        binding.etCitySearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    binding.bClear.visibility = View.VISIBLE
                    mkeyWords = s.toString()
                    binding.bClear.visibility = View.VISIBLE
                    viewModel.getAirportCity("Bearer " + sessionManger.fetchAuthToken()!!, mkeyWords)
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        /**
         * Airport cities Listener Live Data
         */

        viewModel.cities.observe(requireActivity()) {
            binding.rcAirportCities.layoutManager = LinearLayoutManager(requireActivity())
            binding.rcAirportCities.adapter = CityAdapter(it!!, this)
        }

        /**
         * Clear search text
         */

        binding.bClear.setOnClickListener {
            binding.bClear.visibility = View.GONE
            binding.etCitySearch.text.clear()
            mkeyWords = "A"
            viewModel.getAirportCity("Bearer " + sessionManger.fetchAuthToken()!!, mkeyWords)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun cityClickListeners(data: Data) {
        if (mCityType == FROM_CITY) {
            shareViewModel.setAirportCity(data.iataCode)
        } else if (mCityType == TO_CITY) {
            shareViewModel.setAirportToCity(data.iataCode)
        }
        val action = AirportCityFragmentDirections.actionAirportCityFragmentToDashboardFragment()
        findNavController().navigate(action)
    }
}