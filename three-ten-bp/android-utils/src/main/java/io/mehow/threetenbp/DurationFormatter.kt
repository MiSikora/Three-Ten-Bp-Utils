package io.mehow.threetenbp

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.View.LAYOUT_DIRECTION_RTL
import org.threeten.bp.Duration
import java.util.Locale

@Suppress("StringLiteralDuplication")
class DurationFormatter internal constructor(
  private val formatters: Set<PartFormatter>,
  private val partsSeparator: String
) {
  @JvmOverloads fun format(
    duration: Duration,
    context: Context,
    reverseOrder: Boolean = context.preferredLocale.isRtl
  ): String {
    return formattedParts(duration, context, reverseOrder).joinToString(partsSeparator)
  }

  private fun formattedParts(
    duration: Duration,
    context: Context,
    reverseOrder: Boolean
  ): List<String> {
    val highestHierarchy = formatters.map(PartFormatter::hierarchy).min() ?: -1L
    val parts = formatters.mapNotNull { formatter ->
      formatter.format(duration, context.resources, formatter.hierarchy == highestHierarchy)
    }
    return if (reverseOrder) parts.reversed() else parts
  }

  private val Context.preferredLocale: Locale
    get() {
      val configuration = resources.configuration
      return if (Build.VERSION.SDK_INT >= 24) configuration.locales[0] else configuration.locale
    }

  private val Locale.isRtl: Boolean
    get() {
      return TextUtils.getLayoutDirectionFromLocale(this) == LAYOUT_DIRECTION_RTL
    }

  companion object {
    @JvmField val Basic = Builder()
        .withDays()
        .withHours()
        .withMinutes()
        .build()

    @JvmField val Full = Builder()
        .withDays(useShortFormat = false, printZeros = true, valueSeparator = " ")
        .withHours(useShortFormat = false, printZeros = true, valueSeparator = " ")
        .withMinutes(useShortFormat = false, printZeros = true, valueSeparator = " ")
        .withSeconds(useShortFormat = false, printZeros = true, valueSeparator = " ")
        .build()
  }

  class Builder private constructor(private val formatters: Set<PartFormatter>) {
    constructor() : this(emptySet())

    @JvmOverloads fun withDays(
      valueSeparator: String = "",
      useShortFormat: Boolean = true,
      printZeros: Boolean = false
    ): Builder {
      return append(PartFormatter.Days(valueSeparator, useShortFormat, printZeros))
    }

    @JvmOverloads fun withHours(
      valueSeparator: String = "",
      useShortFormat: Boolean = true,
      printZeros: Boolean = false
    ): Builder {
      return append(PartFormatter.Hours(valueSeparator, useShortFormat, printZeros))
    }

    @JvmOverloads fun withMinutes(
      valueSeparator: String = "",
      useShortFormat: Boolean = true,
      printZeros: Boolean = false
    ): Builder {
      return append(PartFormatter.Minutes(valueSeparator, useShortFormat, printZeros))
    }

    @JvmOverloads fun withSeconds(
      valueSeparator: String = "",
      useShortFormat: Boolean = true,
      printZeros: Boolean = false
    ): Builder {
      return append(PartFormatter.Seconds(valueSeparator, useShortFormat, printZeros))
    }

    fun build(partsSeparator: String = " "): DurationFormatter {
      return DurationFormatter(formatters, partsSeparator)
    }

    private fun append(formatter: PartFormatter): Builder {
      return Builder(formatters + formatter)
    }
  }
}
