buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.ProjectConfig.gradleTools)
        classpath(Dependencies.ProjectConfig.kotlinGradlePlugin)
        classpath(Dependencies.ProjectConfig.hiltGradlePlugin)
        classpath(Dependencies.ProjectConfig.safeArgsGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

fun clean(type: Delete) {
    delete(rootProject.buildDir)
}