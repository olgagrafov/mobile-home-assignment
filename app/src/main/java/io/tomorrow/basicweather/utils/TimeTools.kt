package io.tomorrow.basicweather.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object TimeTools {

    /** Returns local time given a timestamp (ISO format) and a timeZoneId **/
    suspend fun resolveLocalTime(timestamp: String, timeZoneId: String): String {
        val timezone = TimeZone.of(timeZoneId)
        val localTime = Instant.parse(timestamp).toLocalDateTime(timezone).time
        return "${localTime.hour}:${localTime.minute}"
    }

    /** Returns the ISO representation that corresponds to the current time, according to UTC **/
    suspend fun nowInUtc(): String {
        return Clock.System.now().toLocalDateTime(TimeZone.UTC).toString() + 'Z'
    }

    /** Returns true whether the given [timestamp] is between [lowerBoundTimestamp] and [upperBoundTimestamp], false otherwise. **/
    fun isTimestampBetweenLimits(timestamp: String, lowerBoundTimestamp: String, upperBoundTimestamp: String): Boolean {
        return timestamp in lowerBoundTimestamp..upperBoundTimestamp
    }
}
