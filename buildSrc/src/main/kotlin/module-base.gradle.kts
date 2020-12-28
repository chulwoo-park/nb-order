plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(App.Sdk.compile)
    buildToolsVersion(App.buildTool)

    defaultConfig {
        minSdkVersion(App.Sdk.min)
        targetSdkVersion(App.Sdk.target)
        versionCode = App.versionCode
        versionName = App.versionName

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        consumerProguardFiles("consumer-rules.pro")
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

    sourceSets {
        getByName("androidTest").java.srcDir("src/androidTest/kotlin")
        getByName("main").java.srcDir("src/main/kotlin")
        getByName("test").java.srcDir("src/test/kotlin")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Dependencies.kotlin)
    testImplementation(Dependencies.Test.junit)
}
