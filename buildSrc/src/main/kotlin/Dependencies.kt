object Dependencies {
    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx}"
        const val coroutineAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx}"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.core}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appcompat}"
        const val viewmodel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}"
        const val livedataKtx =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.Google.material}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.Test.junit}"

        object Mockito {
            const val kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
            const val android = "org.mockito:mockito-android:2.28.2"
        }

        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2"
    }

    object AndroidTest {
        const val core = "androidx.arch.core:core-testing:2.1.0"
        const val junit = "androidx.test.ext:junit:${Versions.AndroidTest.junit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.AndroidTest.espresso}"
    }
}
