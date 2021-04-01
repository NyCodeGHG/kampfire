plugins {
    kotlin("jvm") version "1.4.31"
}

group = "de.nycode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val adventureVersion = "4.7.0"
    implementation("net.kyori", "adventure-api", adventureVersion)
    val coroutinesVersion = "1.4.3"
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutinesVersion)

    // JUnit
    val junitVersion = "5.6.0"
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)

    // Mocking and Assert Libraries
    testImplementation("com.willowtreeapps.assertk", "assertk-jvm", "0.23.1")
    testImplementation("io.mockk", "mockk", "1.10.6")

    testImplementation("org.jetbrains.kotlinx", "kotlinx-coroutines-test", coroutinesVersion)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

kotlin {
    explicitApi()
}

tasks.test {
    useJUnitPlatform()
}
