package io.mehow.threetenbp

import io.kotlintest.matchers.numerics.shouldBeExactly
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.DescribeSpec

class DurationsUtilsSpec : DescribeSpec({
  describe("duration") {
    it("has correct days part") {
      assertAll(DurationGenerator) { duration ->
        val expectedDays = duration.seconds / 86_400
        duration.daysPart shouldBeExactly expectedDays
      }
    }

    it("has correct hours part") {
      assertAll(DurationGenerator) { duration ->
        val expectedHours = (duration.seconds / 3_600) % 24
        duration.hoursPart shouldBeExactly expectedHours
      }
    }

    it("has correct minutes part") {
      assertAll(DurationGenerator) { duration ->
        val expectedMinutes = (duration.seconds / 60) % 60
        duration.minutesPart shouldBeExactly expectedMinutes
      }
    }

    it("has correct seconds part") {
      assertAll(DurationGenerator) { duration ->
        val expectedSeconds = duration.seconds % 60
        duration.secondsPart shouldBeExactly expectedSeconds
      }
    }
  }
})
