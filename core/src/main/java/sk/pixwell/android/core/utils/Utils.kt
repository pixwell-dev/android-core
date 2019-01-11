package sk.pixwell.android.core.utils

import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDateTime
import sk.pixwell.android.core.R
import sk.pixwell.android.core.getDelta
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.*


inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

class TimeAgo {


    private var dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private var timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
    private var timeFromData: String = ""
    private var pastDate: String = ""

    private var context: Context? = null

    fun locale(context: Context): TimeAgo {
        this.context = context
        AndroidThreeTen.init(context)
        return this
    }

    fun getTimeAgo(startDate: LocalDateTime): String {

        //  date counting is done till todays date
        val endDate = LocalDateTime.now(ZoneId.systemDefault())

        //  time difference in milli seconds
        val different = ChronoUnit.MILLIS.between(endDate, startDate)

        if (context == null) {
            if (different < MINUTE_MILLIS) {
                return context!!.resources.getString(R.string.just_now)
            } else if (different < 2 * MINUTE_MILLIS) {
                return context!!.resources.getString(R.string.a_min_ago)
            } else if (different < 50 * MINUTE_MILLIS) {
                return (different / MINUTE_MILLIS).toString().plus(context!!.getString(R.string.mins_ago))
            } else if (different < 90 * MINUTE_MILLIS) {
                return context!!.getString(R.string.a_hour_ago)
            } else if (different < 24 * HOUR_MILLIS) {
                timeFromData = timeFormat.format(startDate)
                return timeFromData
            } else if (different < 48 * HOUR_MILLIS) {
                return context!!.getString(R.string.yesterday)
            } else if (different < 7 * DAY_MILLIS) {
                return (different / DAY_MILLIS).toString().plus(context!!.getString(R.string.days_ago))
            } else if (different < 2 * WEEKS_MILLIS) {
                return (different / WEEKS_MILLIS).toString().plus(context!!.getString(R.string.week_ago))
            } else if (different < 3.5 * WEEKS_MILLIS) {
                return (different / WEEKS_MILLIS).toString().plus(context!!.getString(R.string.weeks_ago))
            } else {
                pastDate = dateFormat.format(startDate)
                return pastDate
            }
        } else {
            if (different < MINUTE_MILLIS) {
                return context!!.resources.getString(R.string.just_now)
            } else if (different < 2 * MINUTE_MILLIS) {
                return context!!.resources.getString(R.string.a_min_ago)
            } else if (different < 50 * MINUTE_MILLIS) {
                return (different / MINUTE_MILLIS).toString().plus(context!!.getString(R.string.mins_ago))
            } else if (different < 90 * MINUTE_MILLIS) {
                return context!!.getString(R.string.a_hour_ago)
            } else if (different < 24 * HOUR_MILLIS) {
                timeFromData = timeFormat.format(startDate)
                return timeFromData
            } else if (different < 48 * HOUR_MILLIS) {
                return context!!.getString(R.string.yesterday)
            } else if (different < 7 * DAY_MILLIS) {
                return (different / DAY_MILLIS).toString().plus(context!!.getString(R.string.days_ago))
            } else if (different < 2 * WEEKS_MILLIS) {
                return (different / WEEKS_MILLIS).toString().plus(context!!.getString(R.string.week_ago))
            } else if (different < 3.5 * WEEKS_MILLIS) {
                return (different / WEEKS_MILLIS).toString().plus(context!!.getString(R.string.weeks_ago))
            } else {
                pastDate = dateFormat.format(startDate)
                return pastDate
            }
        }
    }

    companion object {

        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS
        private const val WEEKS_MILLIS = 7 * DAY_MILLIS
    }
}