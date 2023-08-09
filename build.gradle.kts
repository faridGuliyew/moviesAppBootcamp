// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val navVersion = "2.6.0"
        val daggerVersion = "2.44"
        //Safe Args
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        //Hilt
        classpath ("com.google.dagger:hilt-android-gradle-plugin:$daggerVersion")
    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
}