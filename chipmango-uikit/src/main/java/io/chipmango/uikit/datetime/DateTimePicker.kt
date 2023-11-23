package io.chipmango.uikit.datetime


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.google.android.material.timepicker.MaterialTimePicker
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Composable
fun rememberDatePicker(
    date: LocalDate?,
    positiveButtonText: String,
    negativeButtonText: String,
    onDismiss: () -> Unit = {},
    onCancel: () -> Unit = {},
    onDateSelected: (LocalDate) -> Unit
) = remember {
    val currentDate = date?.atStartOfDay(ZoneId.systemDefault())
        ?.toInstant()
        ?.toEpochMilli()

    MaterialDatePicker.Builder.datePicker()
        .setInputMode(INPUT_MODE_CALENDAR)
        .setPositiveButtonText(positiveButtonText)
        .setNegativeButtonText(negativeButtonText)
        .setCalendarConstraints(
            CalendarConstraints.Builder().build()
        )
        .setSelection(currentDate)
        .build()
        .apply {
            addOnDismissListener {
                onDismiss()
            }
            addOnCancelListener {
                onCancel()
            }
            addOnPositiveButtonClickListener {
                val instant = Instant.ofEpochMilli(it)
                val selectedDate = OffsetDateTime
                    .ofInstant(instant, ZoneOffset.UTC)
                    .atZoneSimilarLocal(ZoneId.systemDefault())
                    .toLocalDate()
                onDateSelected(selectedDate)
            }
        }
}

@Composable
fun rememberTimePicker(
    time: LocalTime,
    positiveButtonText: String,
    negativeButtonText: String,
    onDismiss: () -> Unit = {},
    onCancel: () -> Unit = {},
    onTimeSelected: (LocalTime) -> Unit
) = remember {
    MaterialTimePicker.Builder()
        .setPositiveButtonText(positiveButtonText)
        .setNegativeButtonText(negativeButtonText)
        .setHour(time.hour)
        .setMinute(time.minute)
        .build()
        .apply {
            addOnDismissListener {
                onDismiss()
            }
            addOnCancelListener {
                onCancel()
            }
            addOnPositiveButtonClickListener {
                onTimeSelected(LocalTime.of(hour, minute))
            }
        }
}