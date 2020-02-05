package io.mehow.threetenbp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.kotlintest.shouldBe
import io.mehow.threetenbp.FormatterType.Long
import io.mehow.threetenbp.FormatterType.Medium
import io.mehow.threetenbp.FormatterType.Short
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.threeten.bp.Duration

@RunWith(RobolectricTestRunner::class)
class DurationFormatterTest {
  private val context: Context get() = ApplicationProvider.getApplicationContext()

  @Test fun `all duration parts are properly formatted`() {
    val duration = Duration.ofDays(10) +
        Duration.ofHours(11) +
        Duration.ofMinutes(12) +
        Duration.ofSeconds(13)

    val formattedDuration = DurationFormatter.Full.format(duration, context)

    formattedDuration shouldBe "10 days 11 hours 12 minutes 13 seconds"
  }

  @Test fun `short formatter accounts for days plurality`() {
    val oneDay = Duration.ofDays(1)
    val twoDays = Duration.ofDays(2)
    val formatter = DurationFormatter.Builder()
        .withDays()
        .build(Short)

    val formattedOneDay = formatter.format(oneDay, context)
    val formattedTwoDays = formatter.format(twoDays, context)

    formattedOneDay shouldBe "1d"
    formattedTwoDays shouldBe "2d"
  }

  @Test fun `short formatter accounts for hours plurality`() {
    val oneHour = Duration.ofHours(1)
    val twoHours = Duration.ofHours(2)
    val formatter = DurationFormatter.Builder()
        .withHours()
        .build(Short)

    val formattedOneHour = formatter.format(oneHour, context)
    val formattedTwoHours = formatter.format(twoHours, context)

    formattedOneHour shouldBe "1h"
    formattedTwoHours shouldBe "2h"
  }

  @Test fun `short formatter accounts for minutes plurality`() {
    val oneMinute = Duration.ofMinutes(1)
    val twoMinutes = Duration.ofMinutes(2)
    val formatter = DurationFormatter.Builder()
        .withMinutes()
        .build(Short)

    val formattedOneMinute = formatter.format(oneMinute, context)
    val formattedTwoMinutes = formatter.format(twoMinutes, context)

    formattedOneMinute shouldBe "1min"
    formattedTwoMinutes shouldBe "2min"
  }

  @Test fun `short formatter accounts for seconds plurality`() {
    val oneSecond = Duration.ofSeconds(1)
    val twoSeconds = Duration.ofSeconds(2)
    val formatter = DurationFormatter.Builder()
        .withSeconds()
        .build(Short)

    val formattedOneSecond = formatter.format(oneSecond, context)
    val formattedTwoSeconds = formatter.format(twoSeconds, context)

    formattedOneSecond shouldBe "1s"
    formattedTwoSeconds shouldBe "2s"
  }

  @Test fun `medium formatter accounts for days plurality`() {
    val oneDay = Duration.ofDays(1)
    val twoDays = Duration.ofDays(2)
    val formatter = DurationFormatter.Builder()
        .withDays()
        .build(Medium)

    val formattedOneDay = formatter.format(oneDay, context)
    val formattedTwoDays = formatter.format(twoDays, context)

    formattedOneDay shouldBe "1d"
    formattedTwoDays shouldBe "2d"
  }

  @Test fun `medium formatter accounts for hours plurality`() {
    val oneHour = Duration.ofHours(1)
    val twoHours = Duration.ofHours(2)
    val formatter = DurationFormatter.Builder()
        .withHours()
        .build(Medium)

    val formattedOneHour = formatter.format(oneHour, context)
    val formattedTwoHours = formatter.format(twoHours, context)

    formattedOneHour shouldBe "1hr"
    formattedTwoHours shouldBe "2hrs"
  }

  @Test fun `medium formatter accounts for minutes plurality`() {
    val oneMinute = Duration.ofMinutes(1)
    val twoMinutes = Duration.ofMinutes(2)
    val formatter = DurationFormatter.Builder()
        .withMinutes()
        .build(Medium)

    val formattedOneMinute = formatter.format(oneMinute, context)
    val formattedTwoMinutes = formatter.format(twoMinutes, context)

    formattedOneMinute shouldBe "1min"
    formattedTwoMinutes shouldBe "2mins"
  }

  @Test fun `medium formatter accounts for seconds plurality`() {
    val oneSecond = Duration.ofSeconds(1)
    val twoSeconds = Duration.ofSeconds(2)
    val formatter = DurationFormatter.Builder()
        .withSeconds()
        .build(Medium)

    val formattedOneSecond = formatter.format(oneSecond, context)
    val formattedTwoSeconds = formatter.format(twoSeconds, context)

    formattedOneSecond shouldBe "1sec"
    formattedTwoSeconds shouldBe "2secs"
  }

  @Test fun `long formatter accounts for days plurality`() {
    val oneDay = Duration.ofDays(1)
    val twoDays = Duration.ofDays(2)
    val formatter = DurationFormatter.Builder()
        .withDays()
        .build(Long)

    val formattedOneDay = formatter.format(oneDay, context)
    val formattedTwoDays = formatter.format(twoDays, context)

    formattedOneDay shouldBe "1day"
    formattedTwoDays shouldBe "2days"
  }

  @Test fun `long formatter accounts for hours plurality`() {
    val oneHour = Duration.ofHours(1)
    val twoHours = Duration.ofHours(2)
    val formatter = DurationFormatter.Builder()
        .withHours()
        .build(Long)

    val formattedOneHour = formatter.format(oneHour, context)
    val formattedTwoHours = formatter.format(twoHours, context)

    formattedOneHour shouldBe "1hour"
    formattedTwoHours shouldBe "2hours"
  }

  @Test fun `long formatter accounts for minutes plurality`() {
    val oneMinute = Duration.ofMinutes(1)
    val twoMinutes = Duration.ofMinutes(2)
    val formatter = DurationFormatter.Builder()
        .withMinutes()
        .build(Long)

    val formattedOneMinute = formatter.format(oneMinute, context)
    val formattedTwoMinutes = formatter.format(twoMinutes, context)

    formattedOneMinute shouldBe "1minute"
    formattedTwoMinutes shouldBe "2minutes"
  }

  @Test fun `long formatter accounts for seconds plurality`() {
    val oneSecond = Duration.ofSeconds(1)
    val twoSeconds = Duration.ofSeconds(2)
    val formatter = DurationFormatter.Builder()
        .withSeconds()
        .build(Long)

    val formattedOneSecond = formatter.format(oneSecond, context)
    val formattedTwoSeconds = formatter.format(twoSeconds, context)

    formattedOneSecond shouldBe "1second"
    formattedTwoSeconds shouldBe "2seconds"
  }

  @Test fun `formatter handles zero days value`() {
    val zeroFormatter = DurationFormatter.Builder()
        .withDays(printZeros = true)
        .build()

    val durationWithZero = zeroFormatter.format(Duration.ZERO, context)

    durationWithZero shouldBe "0d"
  }

  @Test fun `formatter handles zero hours value`() {
    val zeroFormatter = DurationFormatter.Builder()
        .withDays()
        .withHours(printZeros = true)
        .build()
    val noZeroFormatter = DurationFormatter.Builder()
        .withDays()
        .withHours()
        .build()

    val durationWithZero = zeroFormatter.format(Duration.ofDays(1), context)
    val durationWithoutZero = noZeroFormatter.format(Duration.ofDays(1), context)

    durationWithZero shouldBe "1d 0h"
    durationWithoutZero shouldBe "1d"
  }

  @Test fun `formatter handles zero minutes value`() {
    val zeroFormatter = DurationFormatter.Builder()
        .withHours()
        .withMinutes(printZeros = true)
        .build()
    val noZeroFormatter = DurationFormatter.Builder()
        .withHours()
        .withMinutes()
        .build()

    val durationWithZero = zeroFormatter.format(Duration.ofHours(1), context)
    val durationWithoutZero = noZeroFormatter.format(Duration.ofHours(1), context)

    durationWithZero shouldBe "1h 0min"
    durationWithoutZero shouldBe "1h"
  }

  @Test fun `formatter handles zero seconds value`() {
    val zeroFormatter = DurationFormatter.Builder()
        .withMinutes()
        .withSeconds(printZeros = true)
        .build()
    val noZeroFormatter = DurationFormatter.Builder()
        .withMinutes()
        .withSeconds()
        .build()

    val durationWithZero = zeroFormatter.format(Duration.ofMinutes(1), context)
    val durationWithoutZero = noZeroFormatter.format(Duration.ofMinutes(1), context)

    durationWithZero shouldBe "1min 0s"
    durationWithoutZero shouldBe "1min"
  }

  @Test fun `formatter can be have any order of parts`() {
    val formatter = DurationFormatter.Builder()
        .withSeconds(printZeros = true)
        .withMinutes(printZeros = true)
        .withHours(printZeros = true)
        .withDays(printZeros = true)
        .build()

    val formattedDuration = formatter.format(Duration.ZERO, context)

    formattedDuration shouldBe "0s 0min 0h 0d"
  }

  @Test fun `hours formatter accumulates days part`() {
    val duration = Duration.ofDays(10) + Duration.ofHours(5)
    val formatter = DurationFormatter.Builder()
        .withSeconds()
        .withMinutes()
        .withHours()
        .build()

    val formattedDuration = formatter.format(duration, context)

    formattedDuration shouldBe "245h"
  }

  @Test fun `minutes formatter accumulates days and hours part`() {
    val duration = Duration.ofDays(10) + Duration.ofHours(5) + Duration.ofMinutes(10)
    val formatter = DurationFormatter.Builder()
        .withSeconds()
        .withMinutes()
        .build()

    val formattedDuration = formatter.format(duration, context)

    formattedDuration shouldBe "14710min"
  }

  @Test fun `seconds formatter accumulates days, hours and minutes part`() {
    val duration = Duration.ofDays(10) +
        Duration.ofHours(5) +
        Duration.ofMinutes(10) +
        Duration.ofSeconds(25)
    val formatter = DurationFormatter.Builder()
        .withSeconds()
        .build()

    val formattedDuration = formatter.format(duration, context)

    formattedDuration shouldBe "882625s"
  }

  @Test @Config(qualifiers = "ar") fun `parts are reversed for RTL locales`() {
    val formattedDuration = DurationFormatter.Full.format(Duration.ZERO, context)

    formattedDuration shouldBe "0 ثانية 0 دقيقة 0 ساعة 0 يوم"
  }

  @Test fun `zero days are printed when no day duration is present for days formatter`() {
    val distance = Duration.ofHours(23) + Duration.ofMinutes(2) + Duration.ofSeconds(11)
    val formatter = DurationFormatter.Builder()
        .withDays(printZeros = false)
        .build()

    val formattedDuration = formatter.format(distance, context)

    formattedDuration shouldBe "0d"
  }

  @Test fun `zero hours are printed when no hour duration is present for hours formatter`() {
    val distance = Duration.ofMinutes(2) + Duration.ofSeconds(11)
    val formatter = DurationFormatter.Builder()
        .withDays(printZeros = false)
        .withHours(printZeros = false)
        .build()

    val formattedDuration = formatter.format(distance, context)

    formattedDuration shouldBe "0h"
  }

  @Test fun `zero minutes are printed when no minute duration is present for minutes formatter`() {
    val distance = Duration.ofSeconds(11)
    val formatter = DurationFormatter.Builder()
        .withDays(printZeros = false)
        .withHours(printZeros = false)
        .withMinutes(printZeros = false)
        .build()

    val formattedDuration = formatter.format(distance, context)

    formattedDuration shouldBe "0min"
  }

  @Test fun `zero seconds are printed when no second duration is present for seconds formatter`() {
    val distance = Duration.ZERO
    val formatter = DurationFormatter.Builder()
        .withDays(printZeros = false)
        .withHours(printZeros = false)
        .withMinutes(printZeros = false)
        .withSeconds(printZeros = false)
        .build()

    val formattedDuration = formatter.format(distance, context)

    formattedDuration shouldBe "0s"
  }
}
