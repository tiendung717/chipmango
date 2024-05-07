package io.chipmango.room.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class ChipmangoDateTimeConverter {
    @TypeConverter
    fun fromDateTime(datetime: LocalDateTime): Long {
        return datetime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    @TypeConverter
    fun toDateTime(timestamp: Long): LocalDateTime {
        return Instant
            .ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    @TypeConverter
    fun fromDate(date: LocalDate?): Long {
        return date?.let {
            date
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        } ?: 0L
    }

    @TypeConverter
    fun toDate(timestamp: Long?): LocalDate? {
        return when (timestamp) {
            0L -> null
            else -> timestamp?.let {
                Instant
                    .ofEpochMilli(timestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .toLocalDate()
            }
        }
    }

    @TypeConverter
    fun fromTime(time: LocalTime?): Long {
        return time?.toNanoOfDay() ?: 0L
    }

    @TypeConverter
    fun toTime(nanoSeconds: Long?): LocalTime? {
        return when (nanoSeconds) {
            0L -> null
            else -> nanoSeconds?.let { LocalTime.ofNanoOfDay(nanoSeconds) }
        }
    }
}