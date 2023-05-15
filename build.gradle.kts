// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.1" apply false
    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("org.cqfn.diktat.diktat-gradle-plugin") version "1.2.5"
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}

diktat {
    inputs {
        include("**/*.kt")
        include("**/*.kts")
    }
}
