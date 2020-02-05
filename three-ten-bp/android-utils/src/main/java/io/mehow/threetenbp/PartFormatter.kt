package io.mehow.threetenbp

import android.content.res.Resources
import org.threeten.bp.Duration

internal sealed class PartFormatter {
  abstract val separator: String
  abstract val printZeros: Boolean

  abstract val hierarchy: Int

  abstract val FormatterType.resource: Int

  fun format(resources: Resources, duration: Long, type: FormatterType): String {
    val resource = type.resource
    val quantity = if (duration <= Int.MAX_VALUE) duration.toInt() else Int.MAX_VALUE
    return resources.getQuantityString(resource, quantity, duration, separator)
  }

  fun part(duration: Duration, isBasePart: Boolean): Long {
    val extraPart = if (isBasePart) duration.extractExtraPart() else 0
    return duration.extractPart() + extraPart
  }

  protected abstract fun Duration.extractPart(): Long

  protected abstract fun Duration.extractExtraPart(): Long

  abstract override fun equals(other: Any?): Boolean

  abstract override fun hashCode(): Int

  class Days(
    override val separator: String,
    override val printZeros: Boolean
  ) : PartFormatter() {
    override val hierarchy = 0

    override val FormatterType.resource
      get() = when (this) {
        FormatterType.Long -> R.plurals.io_mehow_threetenbp_days_long
        FormatterType.Medium -> R.plurals.io_mehow_threetenbp_days_medium
        FormatterType.Short -> R.plurals.io_mehow_threetenbp_days_short
      }

    override fun Duration.extractPart() = daysPart

    override fun Duration.extractExtraPart() = 0L

    override fun equals(other: Any?) = other is Days

    override fun hashCode() = 1
  }

  class Hours(
    override val separator: String,
    override val printZeros: Boolean
  ) : PartFormatter() {
    override val hierarchy = 1

    override val FormatterType.resource
      get() = when (this) {
        FormatterType.Long -> R.plurals.io_mehow_threetenbp_hours_long
        FormatterType.Medium -> R.plurals.io_mehow_threetenbp_hours_medium
        FormatterType.Short -> R.plurals.io_mehow_threetenbp_hours_short
      }

    override fun Duration.extractPart() = hoursPart

    override fun Duration.extractExtraPart() = toDays() * 24

    override fun equals(other: Any?) = other is Hours

    override fun hashCode() = 2
  }

  class Minutes(
    override val separator: String,
    override val printZeros: Boolean
  ) : PartFormatter() {
    override val hierarchy = 2

    override val FormatterType.resource
      get() = when (this) {
        FormatterType.Long -> R.plurals.io_mehow_threetenbp_minutes_long
        FormatterType.Medium -> R.plurals.io_mehow_threetenbp_minutes_medium
        FormatterType.Short -> R.plurals.io_mehow_threetenbp_minutes_short
      }

    override fun Duration.extractPart() = minutesPart

    override fun Duration.extractExtraPart() = toHours() * 60

    override fun equals(other: Any?) = other is Minutes

    override fun hashCode() = 3
  }

  class Seconds(
    override val separator: String,
    override val printZeros: Boolean
  ) : PartFormatter() {
    override val hierarchy = 4

    override val FormatterType.resource
      get() = when (this) {
        FormatterType.Long -> R.plurals.io_mehow_threetenbp_seconds_long
        FormatterType.Medium -> R.plurals.io_mehow_threetenbp_seconds_medium
        FormatterType.Short -> R.plurals.io_mehow_threetenbp_seconds_short
      }

    override fun Duration.extractPart() = secondsPart

    override fun Duration.extractExtraPart() = toMinutes() * 60

    override fun equals(other: Any?) = other is Seconds

    override fun hashCode() = 4
  }
}
