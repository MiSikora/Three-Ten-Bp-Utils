@file:JvmName("DurationUtils")

package io.mehow.threetenbp

import org.threeten.bp.Duration

val Duration.daysPart: Long get() = toDays()

val Duration.hoursPart: Long get() = toHours() % 24

val Duration.minutesPart: Long get() = toMinutes() % 60

val Duration.secondsPart: Long get() = seconds % 60
