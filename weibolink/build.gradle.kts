plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "io.github.wangcheng.weibolink"
        minSdk = 16
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    namespace = "io.github.wangcheng.weibolink"
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    testImplementation("junit:junit:4.13.2")
}
