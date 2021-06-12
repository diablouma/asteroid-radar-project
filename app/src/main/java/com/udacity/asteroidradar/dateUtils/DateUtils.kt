package com.udacity.asteroidradar.dateUtils

import com.udacity.asteroidradar.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun toStringRepresentation(date: LocalDate): String {
    return DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT)
        .format(date)
}

fun localDateFrom(date: String): LocalDate {
    return LocalDate.parse(
        date,
        DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT)
    )
}