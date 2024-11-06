import org.gradle.kotlin.dsl.support.kotlinCompilerOptions

plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    // coroutines
    implementation(libs.kotlin.coroutines.core)
    // di
    compileOnly(libs.hilt.compiler)
    // test
    testImplementation(libs.junit)
}