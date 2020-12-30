plugins {
    `module-base`
    `presentation-module-base`
}

dependencies {
    api(project(":features:product:domain"))
    api(project(":features:cart:domain"))
}
