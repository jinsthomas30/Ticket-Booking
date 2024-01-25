package com.example.flightticketingbooking.dashboard.view

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flightticketingbooking.Constants.FROM_CITY
import com.example.flightticketingbooking.Constants.ONE_WAY
import com.example.flightticketingbooking.Constants.ROUND_TRIP
import com.example.flightticketingbooking.Constants.TO_CITY
import com.example.flightticketingbooking.SessionManager
import com.example.flightticketingbooking.dashboard.viewModel.DashboardViewModel
import com.example.flightticketingbooking.databinding.FragmentDashboardBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: DashboardViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[DashboardViewModel::class.java]
        sessionManager = SessionManager(requireActivity())
        viewModel.getToken()
        manageView(ONE_WAY)
        val formatter = DateTimeFormatter.ofPattern("dd, MMM, yy")
        val current = LocalDateTime.now().format(formatter)

        binding.tFromCityValue.text = if (!viewModel.mFromCity.isNullOrEmpty()) {
            viewModel.mFromCity
        } else {
            "-"
        }
        binding.tToCityValue.text = if (!viewModel.mToCity.isNullOrEmpty()) {
            viewModel.mToCity
        } else {
            "-"
        }
        binding.tAdultValue.text = viewModel.passengerCount.toString()
        binding.tFromDate.text = if (!viewModel.mFromDate.isNullOrEmpty()) {
            viewModel.mFromDate
        } else {
            current
        }
        binding.tToDate.text = if (!viewModel.mToDate.isNullOrEmpty()) {
            viewModel.mToDate
        } else {
            current
        }

        val tab: TabLayout.Tab = binding.tabLayout.getTabAt(viewModel.mTripType)!!
        binding.bAdd.setOnClickListener {
            viewModel.addPassenger()
        }
        binding.bMinus.setOnClickListener {
            viewModel.reducePassenger()
        }

        binding.tFromDate.setOnClickListener {
            showDatePickerDialogFromDate()
        }

        binding.tToDate.setOnClickListener {
            showDatePickerDialogToDate()
        }


        /**
        Tablayout change listener*/
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    viewModel.setTripType(ONE_WAY)
                    manageView(ONE_WAY)
                } else {
                    viewModel.setTripType(ROUND_TRIP)
                    manageView(ROUND_TRIP)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        tab.select()
        /**
        From City click listener*/
        binding.tFromCityValue.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToAirportCityFragment(FROM_CITY)
            findNavController().navigate(action)
        }
        /**
        To City click listener*/
        binding.tToCityValue.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToAirportCityFragment(TO_CITY)
            findNavController().navigate(action)
        }
        /**
        Token Listener Live Data*/
        viewModel.token.observe(requireActivity(), Observer {
            println("$$$$it")
            sessionManager.saveAuthToken(it)
        })
        /**
        Passenger Count Live Data*/
        viewModel.adultCount.observe(requireActivity(), Observer {
            binding.tAdultValue.text = it
        })

    }

    private fun showDatePickerDialogFromDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val fromCal = Calendar.getInstance()
                fromCal.timeInMillis = 0 ////disable dates
                fromCal.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd, MMM, yy")
                val dateString: String = dateFormat.format(fromCal.time)
                binding.tFromDate.text = dateString
                binding.tToDate.text = dateString
                viewModel.setToDate(dateString)
                viewModel.setFromDate(dateString)
                viewModel.setFromCalenderInstance(fromCal)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.minDate = c.timeInMillis
        datePickerDialog.show()
    }


    private fun showDatePickerDialogToDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                val cal = Calendar.getInstance()
                cal.timeInMillis = 0 ////disable dates
                cal.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd, MMM, yy")
                val dateString: String = dateFormat.format(cal.time)
                viewModel.setToDate(dateString)
                binding.tToDate.text = dateString

            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.minDate = viewModel.fromCal.timeInMillis
        datePickerDialog.show()
    }

    private fun manageView(tripType: Int) {
        if (tripType == ONE_WAY) {
            binding.tToDate.visibility = View.GONE
        } else if (tripType == ROUND_TRIP) {
            binding.tToDate.visibility = View.VISIBLE
        }

    }
}