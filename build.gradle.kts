plugins {
    kotlin("jvm") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "co.moarr"
version = "0.0.1"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.moarr.co/snapshots")
}

dependencies {
    implementation(kotlin("stdlib"))
    /* Ktor-Network */
    implementation("io.ktor:ktor-network:1.5.2")
    /* Logback, a logging framework */
    implementation("ch.qos.logback:logback-classic:1.3.0-alpha5")
    /* TOML4J, for TOML configurations. */
    implementation("com.moandjiezana.toml:toml4j:0.7.2")
    /* kt-mc-packet, for Kotlin-specific MC packet deserialisation */
    implementation("co.moarr.milkbucket:kt-mc-packet-jvm:0.3.0-SNAPSHOT")
    /* GSON, library for JSON (de)serialisation */
    implementation("com.google.code.gson:gson:2.8.5")
}

tasks.withType<Jar> {
    manifest {
        attributes(mapOf("Main-Class" to "co.moarr.milkbucket.Server"))
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    this.archiveVersion.set("0.0.1")
}