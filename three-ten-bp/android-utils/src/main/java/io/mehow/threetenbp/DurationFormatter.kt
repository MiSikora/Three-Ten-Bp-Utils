package io.mehow.threetenbp

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.View.LAYOUT_DIRECTION_RTL
import io.mehow.threetenbp.FormatterType.Short
import org.threeten.bp.Duration
import java.util.Locale

@Suppress("StringLiteralDuplication")
class DurationFormatter internal constructor(
  private val formatters: Set<PartFormatter>,
  private val partsSeparator: String,
  private val type: FormatterType
) {
  fun format(duration: Duration, context: Context): String {
    val reverseOrder = context.preferredLocale.isRtl
    return formattedParts(duration, context, reverseOrder)
  }

  private fun formattedParts(duration: Duration, context: Context, reverseOrder: Boolean): String {
    val lowestHierarchy = formatters.map(PartFormatter::hierarchy).max()!!
    val highestHierarchy = formatters.map(PartFormatter::hierarchy).min()!!

    val lengthMap = formatters.associateWith { formatter ->
      val isBasePart = formatter.hierarchy == highestHierarchy
      return@associateWith formatter.part(duration, isBasePart)
    }
    val totalLength = lengthMap.values.fold(0L) { acc, length -> acc + length }

    val formattedParts = formatters.mapNotNull { formatter ->
      val part = lengthMap.getValue(formatter)
      return@mapNotNull formatter.printData(context, part, totalLength, lowestHierarchy)
    }

    val localizedParts = if (reverseOrder) formattedParts.reversed() else formattedParts
    return localizedParts.joinToString(partsSeparator)
  }

  private fun PartFormatter.printData(
    context: Context,
    duration: Long,
    totalDuration: Long,
    lowestAvailableHierarchy: Int
  ): String? {
    val resources = context.resources
    return when {
      canPrintData(duration) -> format(resources, duration, type)
      shouldPrintData(totalDuration, lowestAvailableHierarchy) -> format(resources, 0L, type)
      else -> null
    }
  }

  private fun PartFormatter.shouldPrintData(
    totalDuration: Long,
    lowestHierarchy: Int
  ): Boolean {
    return totalDuration == 0L && hierarchy == lowestHierarchy
  }

  private fun PartFormatter.canPrintData(part: Long): Boolean {
    return printZeros || part != 0L
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
        .withDays(printZeros = true, valueSeparator = " ")
        .withHours(printZeros = true, valueSeparator = " ")
        .withMinutes(printZeros = true, valueSeparator = " ")
        .withSeconds(printZeros = true, valueSeparator = " ")
        .build(FormatterType.Long)
  }

  class Builder private constructor(private val formatters: Set<PartFormatter>) {
    constructor() : this(emptySet())

    @JvmOverloads fun withDays(
      valueSeparator: String = "",
      printZeros: Boolean = false
    ): Builder {
      return append(PartFormatter.Days(valueSeparator, printZeros))
    }

    @JvmOverloads fun withHours(
      valueSeparator: String = "",
      printZeros: Boolean = false
    ): Builder {
      return append(PartFormatter.Hours(valueSeparator, printZeros))
    }

    @JvmOverloads fun withMinutes(
      valueSeparator: String = "",
      printZeros: Boolean = false
    ): Builder {
      return append(PartFormatter.Minutes(valueSeparator, printZeros))
    }

    @JvmOverloads fun withSeconds(
      valueSeparator: String = "",
      printZeros: Boolean = false
    ): Builder {
      return append(PartFormatter.Seconds(valueSeparator, printZeros))
    }

    @JvmOverloads fun build(
      type: FormatterType = Short,
      partsSeparator: String = " "
    ): DurationFormatter {
      return DurationFormatter(formatters, partsSeparator, type)
    }

    private fun append(formatter: PartFormatter): Builder {
      return Builder(formatters + formatter)
    }
  }
}
