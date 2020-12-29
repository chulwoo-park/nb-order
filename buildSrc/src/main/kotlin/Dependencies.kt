object Dependencies {
    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx}"
        const val coroutineAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx}"
    }

    object Koin {
        const val android = "org.koin:koin-android:${Versions.koin}"
        const val scope = "org.koin:koin-androidx-scope:${Versions.koin}"
        const val viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
        const val fragment = "org.koin:koin-androidx-fragment:${Versions.koin}"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appcompat}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"

        const val viewmodel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}"
        const val livedata =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"
        const val activity = "androidx.activity:activity-ktx:${Versions.AndroidX.activity}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.AndroidX.fragment}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.Google.material}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    }

    object OkHttp3 {
        const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.Test.junit}"

        object Mockito {
            const val kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
            const val android = "org.mockito:mockito-android:2.28.2"
        }

        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2"

        const val retrofit = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
        const val mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp3}"
    }

    object AndroidTest {
        const val core = "androidx.arch.core:core-testing:2.1.0"
        const val junit = "androidx.test.ext:junit:${Versions.AndroidTest.junit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.AndroidTest.espresso}"
        const val fragment = "androidx.fragment:fragment-testing:${Versions.AndroidX.fragment}"
    }
}
