plugins {
  kotlin("jvm")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

dependencies {
  api(Libs.ThreeTenBp)

  implementation(Libs.Kotlin.StdLibJdk7)

  testImplementation(Libs.KotlinTest.RunnerJunit5)
}

apply(from = rootProject.file("gradle/gradle-mvn-push.gradle"))
