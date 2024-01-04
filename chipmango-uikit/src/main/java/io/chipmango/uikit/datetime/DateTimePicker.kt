package io.chipmango.uikit.datetime


import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import io.chipmango.uikit.container.ClickableContainer
import io.chipmango.uikit.row.TwoLineRow
import java.time.Instant
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

private fun OffsetDateTime.atEndOfDay(): OffsetDateTime {
    return OffsetDateTime.of(toLocalDate(), LocalTime.of(23, 59, 59), offset)
}

private fun OffsetDateTime.atStartOfDay(): OffsetDateTime {
    return OffsetDateTime.of(toLocalDate(), LocalTime.MIDNIGHT, offset)
}

private fun Long.toOffsetDateTime(zoneId: ZoneId): OffsetDateTime {
    val offset = ZonedDateTime.now(zoneId).offset
    val instant = Instant.ofEpochMilli(this)
    return instant.atOffset(offset)
}

@Composable
fun rememberDatePicker(
    currentDateTime: OffsetDateTime,
    startDate: OffsetDateTime? = null,
    positiveButtonText: String,
    negativeButtonText: String,
    onDismiss: () -> Unit = {},
    onCancel: () -> Unit = {},
    onDateSelected: (OffsetDateTime) -> Unit
) = remember(currentDateTime, startDate) {
    val currentDate = currentDateTime.atEndOfDay()
        .toInstant()
        .toEpochMilli()

    val constraints = if (startDate != null) {
        val startAt = startDate.atStartOfDay()
            .toInstant()
            .toEpochMilli()
        CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.from(startAt))
            .build()
    } else null

    MaterialDatePicker.Builder.datePicker()
        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
        .setPositiveButtonText(positiveButtonText)
        .setNegativeButtonText(negativeButtonText)
        .setCalendarConstraints(constraints)
        .setSelection(currentDate)
        .build()
        .apply {
            addOnDismissListener {
                onDismiss()
            }
            addOnCancelListener {
                onCancel()
            }
            addOnPositiveButtonClickListener { timestamp ->
                // The date and time in the DatePicker are expressed in UTC.
                // Therefore, it is necessary to obtain the date and time in UTC.
                val newLocalDate = timestamp.toOffsetDateTime(ZoneOffset.UTC).toLocalDateTime()
                val newDate = OffsetDateTime.of(newLocalDate, currentDateTime.offset)
                val dateTime = newDate
                    .withHour(currentDateTime.hour)
                    .withMinute(currentDateTime.minute)
                    .truncatedTo(ChronoUnit.MINUTES)

                onDateSelected(dateTime)
            }
        }
}

@Composable
fun rememberTimePicker(
    currentDateTime: OffsetDateTime,
    positiveButtonText: String,
    negativeButtonText: String,
    onDismiss: () -> Unit = {},
    onCancel: () -> Unit = {},
    onTimeSelected: (OffsetDateTime) -> Unit
) = remember(currentDateTime) {
    MaterialTimePicker.Builder()
        .setPositiveButtonText(positiveButtonText)
        .setNegativeButtonText(negativeButtonText)
        .setHour(currentDateTime.hour)
        .setMinute(currentDateTime.minute)
        .build()
        .apply {
            addOnDismissListener {
                onDismiss()
            }
            addOnCancelListener {
                onCancel()
            }
            addOnPositiveButtonClickListener {
                val dateTime = currentDateTime
                    .withHour(hour)
                    .withMinute(minute)
                    .truncatedTo(ChronoUnit.MINUTES)
                onTimeSelected(dateTime)
            }
        }
}


@Composable
internal fun DatePicker(
    activity: AppCompatActivity = LocalContext.current as AppCompatActivity
) {
    var date by remember {
        mutableStateOf(OffsetDateTime.now())
    }

    val datePicker = rememberDatePicker(
        currentDateTime = date,
        positiveButtonText = "Select",
        negativeButtonText = "Cancel",
        onDateSelected = {
            date = it
        }
    )
    ClickableContainer(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        shape = RectangleShape,
        containerColor = Color.White,
        onClick = {
            datePicker.show(activity.supportFragmentManager, "")
        }
    ) {
        TwoLineRow(
            modifier = Modifier.fillMaxWidth(),
            title = "Date Picker",
            description = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
            titleTextColor = Color.Black,
            descriptionTextColor = Color.Gray
        )
    }
}

@Composable
internal fun TimePicker(
    activity: AppCompatActivity = LocalContext.current as AppCompatActivity
) {
    var time by remember {
        mutableStateOf(OffsetDateTime.now())
    }

    val datePicker = rememberTimePicker(
        currentDateTime = time,
        positiveButtonText = "Select",
        negativeButtonText = "Cancel",
        onTimeSelected = {
            time = it
        }
    )
    ClickableContainer(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        shape = RectangleShape,
        containerColor = Color.White,
        onClick = {
            datePicker.show(activity.supportFragmentManager, "")
        }
    ) {
        TwoLineRow(
            modifier = Modifier.fillMaxWidth(),
            title = "Time Picker",
            description = time.format(DateTimeFormatter.ofPattern("hh:mm a")),
            titleTextColor = Color.Black,
            descriptionTextColor = Color.Gray
        )
    }
}