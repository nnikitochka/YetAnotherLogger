plugins {
    kotlin("jvm") version "2.1.20"
}

group = "nn.edition.yalogger"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.17")
}

kotlin {
    jvmToolchain(21)
}