plugins {
    id("com.android.application")
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
        textOutput = file("stdout")
    }
}

dependencies {
}
