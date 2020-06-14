package io.mehow.threetenbp

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

class DurationsUtilsSpec : DescribeSpec({
  describe("duration") {
    it("has correct days part") {
      checkAll(DurationGenerator) { duration ->
        val expectedDays = duration.seconds / 86_400
        duration.daysPart shouldBe expectedDays
      }
    }

    it("has correct hours part") {
      checkAll(DurationGenerator) { duration ->
        val expectedHours = (duration.seconds / 3_600) % 24
        duration.hoursPart shouldBe expectedHours
      }
    }

    it("has correct minutes part") {
      checkAll(DurationGenerator) { duration ->
        val expectedMinutes = (duration.seconds / 60) % 60
        duration.minutesPart shouldBe expectedMinutes
      }
    }

    it("has correct seconds part") {
      checkAll(DurationGenerator) { duration ->
        val expectedSeconds = duration.seconds % 60
        duration.secondsPart shouldBe expectedSeconds
      }
    }
  }
})
