plugins {
    kotlin("jvm") version "1.4.31"
    `maven-publish`
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

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "kampfire"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("kampfire")
                description.set("i18n for Kyori Adventure for Java/Kotlin")
                url.set("https://github.com/NyCodeGHG/kampfire")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("nycode")
                        name.set("NyCode")
                        email.set("nico@nycode.de")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/NyCodeGHG/kampfire")
                    developerConnection.set("scm:git:ssh://git@github.com:NyCodeGHG/kampfire.git")
                }
            }
        }
        repositories {
            maven {
                val baseUrl = "https://nycode.jfrog.io/artifactory/"
                val releasesUrl = uri("${baseUrl}nycode-releases/")
                val snapshotsUrl = uri("${baseUrl}nycode-snapshots/")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsUrl else releasesUrl

                val artifactoryUsername = System.getenv("ARTIFACTORY_USERNAME")
                val artifactoryPassword = System.getenv("ARTIFACTORY_PASSWORD")
                credentials {
                    username = artifactoryUsername
                    password = artifactoryPassword
                }
            }
        }
    }
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
