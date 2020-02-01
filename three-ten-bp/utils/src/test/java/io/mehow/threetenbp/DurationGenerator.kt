package io.mehow.threetenbp

import io.kotlintest.properties.Gen
import org.threeten.bp.Duration

object DurationGenerator : Gen<Duration> {
  override fun constants() = listOf(Duration.ZERO, Duration.ofSeconds(Long.MAX_VALUE))

  override fun random() = generateSequence {
    Duration.ofSeconds(Gen.long().random().first())
  }
}
