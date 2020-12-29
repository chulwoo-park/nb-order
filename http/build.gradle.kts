plugins {
    `module-base`
}

dependencies {
    api(Dependencies.Retrofit.retrofit)

    implementation(project(":features:product:data"))
    implementation(Dependencies.Retrofit.moshiConverter)
    implementation(Dependencies.OkHttp3.logging)
    testImplementation(Dependencies.Test.mockwebserver)
}
