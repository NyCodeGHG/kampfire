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
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

kotlin {
    explicitApi()
}
