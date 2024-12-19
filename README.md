# Videos App

This project is an example of an Android app that showcases videos using the OMDB API. It is written following the **MVVM pattern** and **Clean Architecture** with a domain-driven approach. The app includes examples of **BDD unit tests** and **UI tests** following the **Robot pattern**.

## Project Setup

The latest version of the project has been developed with **Android Studio Ladybug | 2024.2.1 Patch 2**. The project requires a JVM compatible with version **17 SDK** to compile.

### API Key Requirement

- **API Key**:
  - The `API_KEY` must be provided inside the `RemoteVideoDataSourceImpl` class. This key is required to authenticate requests to the OMDB API and ensure proper access control.

---

## Project Dependencies

This section outlines the libraries and SDKs utilized in this Android project, their purposes, and the reasons for their inclusion.

### Dependency Injection

- **Hilt**:
  - **Dependencies**:
    ```kotlin
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    ```
  - **Purpose**: Hilt provides a robust framework for dependency injection, ensuring compile-time correctness, performance, scalability, and Android Studio integration.

---

### Data Storage

- **Room Database**:
  - **Dependencies**:
    ```kotlin
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    ```
  - **Purpose**: Room is an abstraction layer over SQLite that enhances database access. It works seamlessly with Kotlin Coroutines, ensuring non-blocking database operations.

---

### Networking

- **Ktor Client**:
  - **Dependencies**:
    ```kotlin
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    ```
  - **Purpose**: Ktor is a coroutine-based toolkit for building asynchronous clients. It supports HTTP calls, content logging, and JSON serialization via `kotlinx.serialization`.

---

### UI Components

- **Jetpack Compose**:
  - **Dependencies**:
    ```kotlin
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.compose.ui.viewbinding)
    ```
  - **Purpose**: Jetpack Compose simplifies UI development with modern Kotlin APIs, reducing boilerplate code. The BOM ensures consistent Compose library versions.

- **AndroidX AppCompat**:
  - **Dependencies**:
    ```kotlin
    implementation(libs.androidx.appcompat)
    ```
  - **Purpose**: AppCompat provides backward compatibility for core Android components, ensuring functionality across different Android versions.

- **Material Design**:
  - **Dependencies**:
    ```kotlin
    implementation(libs.material)
    ```
  - **Purpose**: Material Design components offer consistent, expressive UI elements, adhering to Material Design guidelines.

---

### Lifecycle Management

- **AndroidX Lifecycle Components**:
  - **Dependencies**:
    ```kotlin
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    ```
  - **Purpose**: Manage UI-related data in a lifecycle-conscious way. The `ViewModel` class ensures data survives configuration changes.

- **Fragments**:
  - **Dependencies**:
    ```kotlin
    implementation(libs.fragment.ktx)
    ```
  - **Purpose**: Fragment KTX provides Kotlin-specific enhancements, simplifying fragment transactions and lifecycle handling.

- **AndroidX Core**:
  - **Dependencies**:
    ```kotlin
    implementation(libs.androidx.core.ktx)
    ```
  - **Purpose**: Core KTX offers concise and idiomatic Kotlin extensions for interacting with Android components.

---

### Image Loading

- **Coil**:
  - **Dependencies**:
    ```kotlin
    implementation(libs.coil)
    ```
  - **Purpose**: Coil is a lightweight image-loading library integrated with Kotlin Coroutines, enabling asynchronous image loading and caching.

---

### Logging

- **Timber**:
  - **Dependencies**:
    ```kotlin
    implementation(libs.timber)
    ```
  - **Purpose**: Timber is a logging utility that enhances logging practices with automatic tagging and a simple API.

---

### Testing

- **Unit Testing**:
  - **JUnit4**: For writing and running unit tests.

- **UI Testing**:
  - **Espresso**: For testing UI components and interactions.
  - **UiAutomator**: For device interactions.

---
