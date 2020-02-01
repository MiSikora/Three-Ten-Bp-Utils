@file:JvmName("DurationUtils")

package io.mehow.threetenbp

import org.threeten.bp.Duration

val Duration.daysPart: Long get() = seconds / 86_400

val Duration.hoursPart: Long get() = toHours() % 24

val Duration.minutesPart: Long get() = toMinutes() % 60

val Duration.secondsPart: Long get() = seconds % 60

@JvmSynthetic fun Duration.coerceIn(range: ClosedRange<Duration>): Duration = when {
  this < range.start -> range.start
  this > range.endInclusive -> range.endInclusive
  else -> this
}

fun Duration.coerceIn(minimum: Duration, maximum: Duration): Duration = coerceIn(minimum..maximum)

private val maxDuration = Duration.ofSeconds(Long.MAX_VALUE)

fun Duration.coerceAtLeast(minimum: Duration): Duration = coerceIn(minimum, maxDuration)

fun Duration.coerceAtMost(maximum: Duration): Duration = coerceIn(Duration.ZERO, maximum)
