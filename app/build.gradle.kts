plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
    id("koin")
}

android {
    compileSdkVersion(App.Sdk.compile)
    buildToolsVersion(App.buildTool)

    defaultConfig {
        applicationId = App.id
        minSdkVersion(App.Sdk.min)
        targetSdkVersion(App.Sdk.target)
        versionCode = App.versionCode
        versionName = App.versionName

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("androidTest").java.srcDir("src/androidTest/kotlin")
        getByName("main").java.srcDir("src/main/kotlin")
        getByName("test").java.srcDir("src/test/kotlin")
    }
}

dependencies {
    implementation(project(":ui"))
    implementation(project(":device"))
    implementation(project(":http"))
    implementation(project(":features:product:domain"))
    implementation(project(":features:product:data"))
    implementation(project(":features:product:presentation"))
    implementation(Dependencies.Kotlin.stdlib)
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.viewmodel)
}
