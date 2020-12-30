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

    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/domain_debug.kotlin_module")
        exclude("META-INF/presentation_debug.kotlin_module")
        exclude("META-INF/data_debug.kotlin_module")
    }
}

dependencies {

    implementation(Dependencies.Kotlin.stdlib)
    implementation(Dependencies.Kotlin.coroutine)
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.Mockito.kotlin)
    testImplementation(Dependencies.Test.coroutine)
}
