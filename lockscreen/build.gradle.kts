plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "io.github.wangcheng678.lockscreen" // old package name
        minSdk = 28
        targetSdk = 33
        versionCode = 9
        versionName = "1.3.2"
    }

    namespace = "io.github.wangcheng.lockscreen"

    lint {
        disable += "UnknownNullness"
        disable += "MonochromeLauncherIcon"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
}
