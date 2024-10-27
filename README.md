# Project Set Ups

The latest version of the project has been developed with Android Studio Iguana | 2023.2.1 Patch.

## API Key Requirement

- **API Key**:
  - The `API_KEY` needs to be provided inside the `RemoteVideoDataSourceImpl` class. This key is essential for authenticating requests to the video API and ensuring proper access control. Make sure to keep the key secure and do not expose it in public repositories.

# Project Dependencies

This document details the libraries and SDKs utilized in this Android project, focusing on their purpose and the reasons for their inclusion.

## Dependency Injection

- **Hilt**:
  - `implementation(libs.hilt.android)`
  - `kapt(libs.hilt.compiler)`
  - **Key Points**: Dependency Injection
  - **Purpose**: Hilt provides a robust dependency injection framework for Android, simplifying the DI setup and providing compile-time correctness, performance, scalability, and Android Studio support.

## Room Database & Coroutines

- **Room**:
  - `implementation(libs.room.runtime)`
  - `implementation(libs.room.ktx)`
  - `kapt(libs.room.compiler)`
  - **Key Points**: Caching data
  - **Purpose**: Room is an abstraction layer over SQLite designed to enhance database access. The `KTX` version provides Kotlin extensions for more concise and idiomatic code. Room works well with Kotlin Coroutines to ensure database operations do not block the UI thread.

## HTTP Networking with Ktor

- **Ktor Client**:
  - `implementation(libs.ktor.client.core)`
  - `implementation(libs.ktor.client.android)`
  - `implementation(libs.ktor.client.content.logging)`
  - `implementation(libs.ktor.client.content.negotiation)`
  - `implementation(libs.ktor.serialization.kotlinx.json)`
  - **Key Points**: HTTP calls
  - **Purpose**: Ktor is a coroutine-based toolkit for building asynchronous servers and clients in connected systems. This selection provides core functionality, Android-specific features, and logging capabilities. Content negotiation and serialization with `kotlinx.serialization` enable seamless handling and parsing of JSON data.

## UI Components

- **Jetpack Compose**:
  - `implementation(libs.androidx.activity.compose)`
  - `implementation(platform(libs.androidx.compose.bom))`
  - `implementation(libs.compose.ui.viewbinding)`
  - **Key Points**: JetPack Compose support, AndroidViewBinding, Bill of Materials (reconciliation of JetPack Compose libraries)
  - **Purpose**: Jetpack Compose is a modern toolkit for building native UIs in Android. It simplifies UI development with less code, powerful tools, and intuitive Kotlin APIs. The BOM (Bill of Materials) provides consistent versioning for all Compose libraries.

- **AndroidX AppCompat**:
  - `implementation(libs.androidx.appcompat)`
  - **Key Points**: AppCompatActivity with Support Fragment Manager
  - **Purpose**: AppCompat is used for backward-compatible versions of core Android components, ensuring that apps function smoothly across older and newer Android versions.

- **Material Design**:
  - `implementation(libs.material)`
  - **Key Points**: Theme, Styles, RecyclerView
  - **Purpose**: Material Design components offer UI tools that help to build expressive and consistent UIs, following Material Design principles. It provides widgets like buttons, cards, and text fields, giving the app a cohesive look and feel.

## Lifecycle Management

- **AndroidX Lifecycle Components**:
  - `implementation(libs.lifecycle.runtime.ktx)`
  - `implementation(libs.lifecycle.viewmodel.ktx)`
  - **Key Points**: View Model Scope, ViewModel, onCleared()
  - **Purpose**: Lifecycle components help manage UI-related data in a lifecycle-conscious manner. The `ViewModel` class is used to store and manage UI-related data in a way that survives configuration changes, and `LifecycleRuntime` helps in reacting to changes in the lifecycle of activities or fragments.

- **Fragments**:
  - `implementation(libs.fragment.ktx)`
  - **Key Points**: by viewModels<>() provider, viewLifecycleOwner.lifecycleScope, repeatOnLifecycle 
  - **Purpose**: Fragment KTX provides Kotlin-specific enhancements for Android's Fragment API. This includes easier fragment transactions and other utilities that make working with fragments more concise and idiomatic.

- **AndroidX Core**:
  - `implementation(libs.androidx.core.ktx)`
  - **Key Points**: requireContext(), extensions for interacting with views
  - **Purpose**: The Core KTX library provides Kotlin extensions that make Android development more concise and idiomatic. This includes simpler ways to work with activities, fragments, collections, and more.

## Image Loading

- **Coil**:
  - `implementation(libs.coil)`
  - **Purpose**: Coil is an image loading library for Android that is fast, lightweight, and built with Kotlin. It is fully integrated with Kotlin Coroutines, making it easy to load images asynchronously and apply caching strategies to enhance performance and user experience.

## Logging

- **Timber**:
  - `implementation(libs.timber)`
  - **Purpose**: Timber is a logging utility that simplifies logging practices in Android. It provides automatic tagging of log messages, and its API is an extension over Android's Log class, making it a minimal yet powerful tool for logging in Android applications.

