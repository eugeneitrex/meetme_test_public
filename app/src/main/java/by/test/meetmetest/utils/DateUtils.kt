package by.test.meetmetest.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        private const val DATE_FORMAT = "MM/dd/yyyy"
        private const val TIME_FORMAT = "HH:mm"

        /**
         * Returning Date from requested format
         *
         * @param timestamp current timestamp in mills (String)
         *
         * @return String of required format
         */
        fun getDate(timestamp: String) : String {
            return try {
                val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
                val netDate = Date(timestamp.toLong() * 1000)
                sdf.format(netDate)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        /**
         * Returning Time from requested format
         *
         * @param timestamp current timestamp in mills (String)
         *
         * @return String of required format
         */
        fun getTime(timestamp: String) : String {
            return try {
                val sdf = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
                val netDate = Date(timestamp.toLong() * 1000)
                sdf.format(netDate)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }
    }
}
