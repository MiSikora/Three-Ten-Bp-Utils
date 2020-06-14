plugins {
  id("com.android.library")
  kotlin("android")
}


android {
  resourcePrefix("io_mehow_threetenbp_")

  testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
  api(project(":three-ten-bp:utils"))

  implementation(Libs.Kotlin.StdLibJdk7)

  testImplementation(Libs.JUnit)
  testImplementation(Libs.Kotest.Assertions)
  testImplementation(Libs.AndroidX.Test.CoreKtx)
  testImplementation(Libs.Robolectric)
}

apply(from = rootProject.file("gradle/gradle-mvn-push.gradle"))
