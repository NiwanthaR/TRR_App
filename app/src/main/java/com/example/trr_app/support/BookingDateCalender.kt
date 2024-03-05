package com.example.trr_app.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.example.trr_app.R
import com.example.trr_app.common.BaseActivity
import com.example.trr_app.model.BookingDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BookingDateCalender : DialogFragment(){

    private lateinit var bookingDateRanges: List<BookingDate>
    private lateinit var roomName : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.booking_date_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventDays = getEventDaysFromDateRanges()

        // Find the CalendarView within the inflated layout
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)

        //set Text
        view.findViewById<TextView>(R.id.calenderRoomTitle).text = roomName

        
        // Set the event days to the CalendarView
        calendarView.setEvents(eventDays)

        // Scroll the CalendarView to the first date of the booking range
        val firstBookingDate = eventDays.firstOrNull()?.calendar
        firstBookingDate?.let {
            val calendar = Calendar.getInstance()
            calendar.time = it.time
            calendar.add(Calendar.MONTH, -1) // To show previous month
            calendarView.setMinimumDate(calendar)
        }
    }

    private fun getEventDaysFromDateRanges(): List<EventDay> {
        val eventDays = mutableListOf<EventDay>()

        for (dateRange in bookingDateRanges) {
            try {
                val calendarCheckIn: Calendar = Calendar.getInstance()
                calendarCheckIn.time = dateRange.startDate

                val calendarCheckOut: Calendar = Calendar.getInstance()
                calendarCheckOut.time = dateRange.endDate

                val bookingDates = getDatesBetween(calendarCheckIn, calendarCheckOut)
                for (bookingDate in bookingDates) {
                    val eventDay = EventDay(bookingDate, R.drawable.ic_custom_event_indicator)
                    eventDays.add(eventDay)
                }

            } catch (e: Exception) {
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
        fun newInstance(bookingDateRanges: List<BookingDate>,roomName:String): BookingDateCalender {
            val fragment = BookingDateCalender()
            fragment.bookingDateRanges = bookingDateRanges
            fragment.roomName = roomName
            return fragment
        }
    }
}