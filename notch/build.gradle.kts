plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "io.github.wangcheng.notch"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    namespace = "io.github.wangcheng.notch"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
