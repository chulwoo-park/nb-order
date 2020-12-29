allprojects {
    repositories {
        google()
        jcenter()
    }
}

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath(kotlin("gradle-plugin", "1.4.21"))
        classpath("org.koin:koin-gradle-plugin:${Versions.koin}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
