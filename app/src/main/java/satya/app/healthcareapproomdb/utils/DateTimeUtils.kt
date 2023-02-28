package satya.app.healthcareapproomdb.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {

    companion object {
        fun getConvertedDate(
            dateTime: String, currentFormat: String, convertFormat: String
        ): String {
            var formattedDate = ""
            try {
                val df = SimpleDateFormat(currentFormat, Locale.ENGLISH)
                val displayFormat = SimpleDateFormat(convertFormat, Locale.ENGLISH)
                df.timeZone = TimeZone.getTimeZone("UTC")
                val date = df.parse(dateTime)
                df.timeZone = TimeZone.getDefault()
                formattedDate = displayFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return formattedDate
        }

        // method to generate the date and day of next 10 day from the current date.
        fun loopNextDates(calendar: Calendar): MutableMap<String, String> {
            val format = SimpleDateFormat(Constants.dateFormat)

            val next10Dates = mutableMapOf<String, String>()
            next10Dates[getConvertedDate(
                format.format(Date()), Constants.dateFormat, Constants.dateMonthFormat
            )] = getConvertedDate(format.format(Date()), Constants.dateFormat, Constants.dayFormat)

            for (i in 0..9) {
                //this will take care of month and year when adding 1 day to current date
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                val date = getConvertedDate(
                    format.format(calendar.time), Constants.dateFormat, Constants.dateMonthFormat
                )
                val day = getConvertedDate(
                    format.format(calendar.time), Constants.dateFormat, Constants.dayFormat
                )
                next10Dates[date] = day
            }
            return next10Dates
        }

    }
}