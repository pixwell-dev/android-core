package sk.pixwell.android.core.utils

import android.content.Context
import sk.pixwell.android.core.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

class TimeAgo {

    private var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
    private var dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private var timeFormat: DateFormat = SimpleDateFormat("h:mm aa")
    private var dateTimeNow: Date = Date()
    private var timeFromData: String = ""
    private var pastDate: String = ""
    private var sDateTimeNow: String

    private var context: Context? = null

    init {

        val now = Date()
        sDateTimeNow = simpleDateFormat.format(now)

        try {
            dateTimeNow = simpleDateFormat.parse(sDateTimeNow)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }

    fun locale(context: Context): TimeAgo {
        this.context = context
        return this
    }

    fun with(simpleDateFormat: SimpleDateFormat): TimeAgo {
        this.simpleDateFormat = simpleDateFormat
        this.dateFormat = SimpleDateFormat(simpleDateFormat.toPattern().split(" ")[0])
        this.timeFormat = SimpleDateFormat(simpleDateFormat.toPattern().split(" ")[1])
        return this
    }

    fun getTimeAgo(startDate: Date): String {

        //  date counting is done till todays date
        val endDate = dateTimeNow

        //  time difference in milli seconds
        val different = endDate.getTime() - startDate.getTime()

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