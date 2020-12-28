plugins {
    id("com.android.application")
    kotlin("android")
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

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Dependencies.kotlin)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.Google.material)
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.AndroidTest.junit)
    androidTestImplementation(Dependencies.AndroidTest.espresso)
}
