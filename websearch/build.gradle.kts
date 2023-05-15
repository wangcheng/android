plugins {
    id("com.android.application")
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
        textOutput = file("stdout")
    }
}

dependencies {
}
