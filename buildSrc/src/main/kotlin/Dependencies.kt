object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.core}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appcompat}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.Google.material}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.Test.junit}"

        object Mockito {
            const val core = "org.mockito:mockito-core:${Versions.Test.mockito}"
            const val kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
        }

        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2"
    }

    object AndroidTest {
        const val junit = "androidx.test.ext:junit:${Versions.AndroidTest.junit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.AndroidTest.espresso}"
    }
}
