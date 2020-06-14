package io.mehow.threetenbp

import io.kotest.property.Arb
import io.kotest.property.RandomSource
import io.kotest.property.Sample
import org.threeten.bp.Duration

object DurationGenerator : Arb<Duration>() {
  override fun values(rs: RandomSource) = generateSequence {
    Sample(Duration.ofSeconds(rs.random.nextLong()))
  }

  override fun edgecases() = listOf(Duration.ZERO, Duration.ofSeconds(Long.MAX_VALUE))
}
