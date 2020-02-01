package io.mehow.threetenbp

import android.content.res.Resources
import org.threeten.bp.Duration

internal sealed class PartFormatter {
  abstract val separator: String
  abstract val useShortFormat: Boolean
  abstract val printZeros: Boolean

  abstract val hierarchy: Int

  abstract val longFormatResource: Int
  abstract val shortFormatResource: Int

  fun format(duration: Duration, resources: Resources, isBasePart: Boolean): String? {
    val part = duration.extractPart() + if (isBasePart) duration.extractBasePart() else 0L
    return if (printZeros || part != 0L) {
      val resource = if (useShortFormat) shortFormatResource else longFormatResource
      val quantity = if (part <= Int.MAX_VALUE) part.toInt() else Int.MAX_VALUE
      resources.getQuantityString(resource, quantity, part, separator)
    } else null
  }

  protected abstract fun Duration.extractPart(): Long

  protected abstract fun Duration.extractBasePart(): Long

  abstract override fun equals(other: Any?): Boolean

  abstract override fun hashCode(): Int

  class Days(
    override val separator: String,
    override val useShortFormat: Boolean,
    override val printZeros: Boolean
  ) : PartFormatter() {
    override val hierarchy = 0

    override val longFormatResource = R.plurals.io_mehow_threetenbp_days_long
    override val shortFormatResource = R.plurals.io_mehow_threetenbp_days_short

    override fun Duration.extractPart() = daysPart

    override fun Duration.extractBasePart() = 0L

    override fun equals(other: Any?) = other is Days

    override fun hashCode() = 1
  }

  class Hours(
    override val separator: String,
    override val useShortFormat: Boolean,
    override val printZeros: Boolean
  ) : PartFormatter() {
    override val hierarchy = 1

    override val longFormatResource = R.plurals.io_mehow_threetenbp_hours_long
    override val shortFormatResource = R.plurals.io_mehow_threetenbp_hours_short

    override fun Duration.extractPart() = hoursPart

    override fun Duration.extractBasePart() = daysPart * 24

    override fun equals(other: Any?) = other is Hours

    override fun hashCode() = 2
  }

  class Minutes(
    override val separator: String,
    override val useShortFormat: Boolean,
    override val printZeros: Boolean
  ) : PartFormatter() {
    override val hierarchy = 2

    override val longFormatResource = R.plurals.io_mehow_threetenbp_minutes_long
    override val shortFormatResource = R.plurals.io_mehow_threetenbp_minutes_short

    override fun Duration.extractPart() = minutesPart

    override fun Duration.extractBasePart() = (daysPart * 24 + hoursPart) * 60

    override fun equals(other: Any?) = other is Minutes

    override fun hashCode() = 3
  }

  class Seconds(
    override val separator: String,
    override val useShortFormat: Boolean,
    override val printZeros: Boolean
  ) : PartFormatter() {
    override val hierarchy = 4

    override val longFormatResource = R.plurals.io_mehow_threetenbp_seconds_long
    override val shortFormatResource = R.plurals.io_mehow_threetenbp_seconds_short

    override fun Duration.extractPart() = secondsPart

    override fun Duration.extractBasePart() = ((daysPart * 24 + hoursPart) * 60 + minutesPart) * 60

    override fun equals(other: Any?) = other is Seconds

    override fun hashCode() = 4
  }
}
