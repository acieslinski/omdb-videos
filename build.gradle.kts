// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.autonomousapps.dependency-analysis") version "1.22.0"
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.daggerHiltAndroid) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
}