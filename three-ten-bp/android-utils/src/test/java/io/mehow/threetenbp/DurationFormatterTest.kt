package io.mehow.threetenbp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.kotlintest.matchers.string.shouldBeEmpty
import io.kotlintest.shouldBe
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
        .build()

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
        .build()

    val formattedOneHour = formatter.format(oneHour, context)
    val formattedTwoHours = formatter.format(twoHours, context)

    formattedOneHour shouldBe "1hr"
    formattedTwoHours shouldBe "2hrs"
  }

  @Test fun `short formatter accounts for minutes plurality`() {
    val oneMinute = Duration.ofMinutes(1)
    val twoMinutes = Duration.ofMinutes(2)
    val formatter = DurationFormatter.Builder()
        .withMinutes()
        .build()

    val formattedOneMinute = formatter.format(oneMinute, context)
    val formattedTwoMinutes = formatter.format(twoMinutes, context)

    formattedOneMinute shouldBe "1min"
    formattedTwoMinutes shouldBe "2mins"
  }

  @Test fun `short formatter accounts for seconds plurality`() {
    val oneSecond = Duration.ofSeconds(1)
    val twoSeconds = Duration.ofSeconds(2)
    val formatter = DurationFormatter.Builder()
        .withSeconds()
        .build()

    val formattedOneSecond = formatter.format(oneSecond, context)
    val formattedTwoSeconds = formatter.format(twoSeconds, context)

    formattedOneSecond shouldBe "1sec"
    formattedTwoSeconds shouldBe "2secs"
  }

  @Test fun `long formatter accounts for days plurality`() {
    val oneDay = Duration.ofDays(1)
    val twoDays = Duration.ofDays(2)
    val formatter = DurationFormatter.Builder()
        .withDays(useShortFormat = false)
        .build()

    val formattedOneDay = formatter.format(oneDay, context)
    val formattedTwoDays = formatter.format(twoDays, context)

    formattedOneDay shouldBe "1day"
    formattedTwoDays shouldBe "2days"
  }

  @Test fun `long formatter accounts for hours plurality`() {
    val oneHour = Duration.ofHours(1)
    val twoHours = Duration.ofHours(2)
    val formatter = DurationFormatter.Builder()
        .withHours(useShortFormat = false)
        .build()

    val formattedOneHour = formatter.format(oneHour, context)
    val formattedTwoHours = formatter.format(twoHours, context)

    formattedOneHour shouldBe "1hour"
    formattedTwoHours shouldBe "2hours"
  }

  @Test fun `long formatter accounts for minutes plurality`() {
    val oneMinute = Duration.ofMinutes(1)
    val twoMinutes = Duration.ofMinutes(2)
    val formatter = DurationFormatter.Builder()
        .withMinutes(useShortFormat = false)
        .build()

    val formattedOneMinute = formatter.format(oneMinute, context)
    val formattedTwoMinutes = formatter.format(twoMinutes, context)

    formattedOneMinute shouldBe "1minute"
    formattedTwoMinutes shouldBe "2minutes"
  }

  @Test fun `long formatter accounts for seconds plurality`() {
    val oneSecond = Duration.ofSeconds(1)
    val twoSeconds = Duration.ofSeconds(2)
    val formatter = DurationFormatter.Builder()
        .withSeconds(useShortFormat = false)
        .build()

    val formattedOneSecond = formatter.format(oneSecond, context)
    val formattedTwoSeconds = formatter.format(twoSeconds, context)

    formattedOneSecond shouldBe "1second"
    formattedTwoSeconds shouldBe "2seconds"
  }

  @Test fun `formatter handles zero days value`() {
    val zeroFormatter = DurationFormatter.Builder()
        .withDays(printZeros = true)
        .build()
    val noZeroFormatter = DurationFormatter.Builder()
        .withDays()
        .build()

    val durationWithZero = zeroFormatter.format(Duration.ZERO, context)
    val durationWithoutZero = noZeroFormatter.format(Duration.ZERO, context)

    durationWithZero shouldBe "0d"
    durationWithoutZero.shouldBeEmpty()
  }

  @Test fun `formatter handles zero hours value`() {
    val zeroFormatter = DurationFormatter.Builder()
        .withHours(printZeros = true)
        .build()
    val noZeroFormatter = DurationFormatter.Builder()
        .withHours()
        .build()

    val durationWithZero = zeroFormatter.format(Duration.ZERO, context)
    val durationWithoutZero = noZeroFormatter.format(Duration.ZERO, context)

    durationWithZero shouldBe "0hrs"
    durationWithoutZero.shouldBeEmpty()
  }

  @Test fun `formatter handles zero minutes value`() {
    val zeroFormatter = DurationFormatter.Builder()
        .withMinutes(printZeros = true)
        .build()
    val noZeroFormatter = DurationFormatter.Builder()
        .withMinutes()
        .build()

    val durationWithZero = zeroFormatter.format(Duration.ZERO, context)
    val durationWithoutZero = noZeroFormatter.format(Duration.ZERO, context)

    durationWithZero shouldBe "0mins"
    durationWithoutZero.shouldBeEmpty()
  }

  @Test fun `formatter handles zero seconds value`() {
    val zeroFormatter = DurationFormatter.Builder()
        .withSeconds(printZeros = true)
        .build()
    val noZeroFormatter = DurationFormatter.Builder()
        .withSeconds()
        .build()

    val durationWithZero = zeroFormatter.format(Duration.ZERO, context)
    val durationWithoutZero = noZeroFormatter.format(Duration.ZERO, context)

    durationWithZero shouldBe "0secs"
    durationWithoutZero.shouldBeEmpty()
  }

  @Test fun `formatter can be have any order of parts`() {
    val formatter = DurationFormatter.Builder()
        .withSeconds(printZeros = true)
        .withMinutes(printZeros = true)
        .withHours(printZeros = true)
        .withDays(printZeros = true)
        .build()

    val formattedDuration = formatter.format(Duration.ZERO, context)

    formattedDuration shouldBe "0secs 0mins 0hrs 0d"
  }

  @Test fun `hours formatter accumulates days part`() {
    val duration = Duration.ofDays(10) + Duration.ofHours(5)
    val formatter = DurationFormatter.Builder()
        .withSeconds()
        .withMinutes()
        .withHours()
        .build()

    val formattedDuration = formatter.format(duration, context)

    formattedDuration shouldBe "245hrs"
  }

  @Test fun `minutes formatter accumulates days and hours part`() {
    val duration = Duration.ofDays(10) + Duration.ofHours(5) + Duration.ofMinutes(10)
    val formatter = DurationFormatter.Builder()
        .withSeconds()
        .withMinutes()
        .build()

    val formattedDuration = formatter.format(duration, context)

    formattedDuration shouldBe "14710mins"
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

    formattedDuration shouldBe "882625secs"
  }

  @Test @Config(qualifiers = "ar") fun `parts are reversed for RTL locales`() {
    val formattedDuration = DurationFormatter.Full.format(Duration.ZERO, context)

    formattedDuration shouldBe "0 seconds 0 minutes 0 hours 0 days"
  }
}
