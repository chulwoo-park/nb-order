plugins {
    `module-base`
    `presentation-module-base`
    kotlin("kapt")
}

android {
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":features:cart:presentation"))
    implementation(project(":features:product:presentation"))
    implementation(Dependencies.Koin.viewmodel)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.activity)
    implementation(Dependencies.AndroidX.fragment)
    implementation(Dependencies.Google.material)
    implementation(Dependencies.Glide.glide)
    kapt(Dependencies.Glide.compiler)
}
