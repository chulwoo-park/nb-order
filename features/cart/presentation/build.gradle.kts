plugins {
    `module-base`
    `presentation-module-base`
}

dependencies {
    api(project(":features:product:presentation"))
    api(project(":features:cart:domain"))
}
