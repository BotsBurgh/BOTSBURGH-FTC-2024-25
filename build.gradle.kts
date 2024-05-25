/**
 * Top-level build file for ftc_app project.
 *
 * It is extraordinarily rare that you will ever need to edit this file.
 */

plugins {
    // Note for FTC Teams: Do not modify this yourself.
    id("com.android.application") version "7.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}

// This is now required because aapt2 has to be downloaded from the
// google() repository beginning with version 3.2 of the Android Gradle Plugin
allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

repositories {
    mavenCentral()
}
