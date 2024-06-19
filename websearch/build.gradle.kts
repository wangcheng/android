plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "io.github.wangcheng678.websearch" // old app id. don't change
        minSdk = 23
        targetSdk = 33
        versionCode = 2
        versionName = "1.0.2"
    }

    namespace = "io.github.wangcheng.websearch"

    lint {
        disable += "UnknownNullness"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
}
