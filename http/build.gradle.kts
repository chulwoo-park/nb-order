plugins {
    `module-base`
}

dependencies {
    implementation(project(":features:category:data"))
    implementation(Dependencies.Retrofit.retrofit)
    testImplementation(Dependencies.Test.mockwebserver)
}
