plugins {
    `module-base`
}

dependencies {
    implementation(project(":features:category:data"))
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.moshiConverter)
    implementation(Dependencies.OkHttp3.logging)
    testImplementation(Dependencies.Test.mockwebserver)
}
