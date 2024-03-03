package com.example.trr_app.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.example.trr_app.R
import com.example.trr_app.model.DateRange
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BookingCalendarDialogFragment : DialogFragment() {

    private lateinit var bookingDateRanges: List<DateRange>

    //not using for any activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_booking_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventDays = getEventDaysFromDateRanges()

        // Find the CalendarView within the inflated layout
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)

        // Set the event days to the CalendarView
        calendarView.setEvents(eventDays)
    }

    private fun getEventDaysFromDateRanges(): List<EventDay> {
        val eventDays = mutableListOf<EventDay>()

        for (dateRange in bookingDateRanges) {
            try {
                // Parse the check-in and checkout strings into Date objects
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val checkInDate: Date = sdf.parse(dateRange.startDate) ?: continue
                val checkOutDate: Date = sdf.parse(dateRange.endDate) ?: continue

                val calendarCheckIn: Calendar = Calendar.getInstance()
                calendarCheckIn.time = checkInDate

                val calendarCheckOut: Calendar = Calendar.getInstance()
                calendarCheckOut.time = checkOutDate

                // Create EventDay objects for each day in the booking range
                val bookingDates = getDatesBetween(calendarCheckIn, calendarCheckOut)
                for (bookingDate in bookingDates) {
                    val eventDay = EventDay(bookingDate, R.drawable.ic_custom_event_indicator)
                    eventDays.add(eventDay)
                }

            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        return eventDays
    }

    private fun getDatesBetween(startDate: Calendar, endDate: Calendar): List<Calendar> {
        val dates = mutableListOf<Calendar>()
        val calendar = startDate.clone() as Calendar

        while (calendar.before(endDate)) {
            val dateToAdd = calendar.clone() as Calendar
            dates.add(dateToAdd)
            calendar.add(Calendar.DATE, 1)
        }
        dates.add(endDate)
        return dates
    }

    companion object {
        fun newInstance(bookingDateRanges: List<DateRange>): BookingCalendarDialogFragment {
            val fragment = BookingCalendarDialogFragment()
            fragment.bookingDateRanges = bookingDateRanges
            return fragment
        }
    }
}