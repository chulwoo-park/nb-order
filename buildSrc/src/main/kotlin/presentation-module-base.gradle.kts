plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    implementation(Dependencies.Kotlin.coroutineAndroid)
    implementation(Dependencies.AndroidX.viewmodel)
    implementation(Dependencies.AndroidX.livedata)

    androidTestImplementation(project(":common:test:presentation"))
    androidTestImplementation(Dependencies.Test.coroutine)
    androidTestImplementation(Dependencies.Test.Mockito.kotlin)
    androidTestImplementation(Dependencies.Test.Mockito.android)
    androidTestImplementation(Dependencies.AndroidTest.core)
    androidTestImplementation(Dependencies.AndroidTest.junit)
    androidTestImplementation(Dependencies.AndroidTest.espresso)
}
