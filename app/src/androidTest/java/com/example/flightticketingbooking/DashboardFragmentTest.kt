package com.example.flightticketingbooking

import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightticketingbooking.dashboard.view.DashboardFragment
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    private lateinit var scenario: FragmentScenario<DashboardFragment>

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // Use ActivityScenarioRule to launch YourActivity for testing
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
        // Use launchFragmentInContainer to create a FragmentScenario for testing
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_FlightTicketingBooking)
    }

    @Test
    fun testFragmentInitialization() {
        // Start the fragment
        scenario.onFragment { fragment ->
            // Check if the From City text is set to "-"
            assertEquals("-", fragment.binding.tFromCityValue.text.toString())

            // Check if the To City text is set to "-"
            assertEquals("-", fragment.binding.tToCityValue.text.toString())

            // Check if the Adult Value text is set to "0" (assuming initial passenger count is 0)
            assertEquals("0", fragment.binding.tAdultValue.text.toString())

            // Check if the default date is set for From Date and To Date

            val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd, MMM, yy"))
            assertEquals(currentDate, fragment.binding.tFromDate.text.toString())
            assertEquals(currentDate, fragment.binding.tToDate.text.toString())

            // Check if the TabLayout is initialized with the correct tab selected
            assertTrue(fragment.binding.tabLayout.getTabAt(fragment.viewModel.mTripType)?.isSelected == true)

            // Check if the visibility of UI elements is as expected for the initial state
            assertTrue(fragment.binding.tToDate.visibility == View.VISIBLE)
        }
    }

}
