plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "com.borzg.towatchlist"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "TMDB_URL", "\"https://www.themoviedb.org/\"")
        buildConfigField("String", "TMDB_IMAGE_URL", "\"https://image.tmdb.org/t/p/\"")
        buildConfigField("String", "TMDB_API_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String", "TMDB_API_KEY", "\"f994e496e6421324b52dd0128305a62d\"")
        buildConfigField("String", "IMDB_ID_URL", "\"https://www.imdb.com/title/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.main
    }
}

dependencies {
    implementation(project(Modules.data))
    implementation(project(Modules.domain))

    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.jUnitExt)
    androidTestImplementation(Dependencies.espresso)

    // Compose
    implementation(Dependencies.Compose.runtime)
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.uiTooling)
    implementation(Dependencies.Compose.themeAdapter)
    implementation(Dependencies.Compose.constraintLayout)
    implementation(Dependencies.Compose.viewModel)
    implementation(Dependencies.Compose.hiltNavigation)
    implementation(Dependencies.Compose.paging)
    implementation(Dependencies.Compose.coil)

    // Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverterGson)
    implementation(Dependencies.gson)
    implementation(Dependencies.okhttpLoggingInterceptor)

    // Hilt
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)

    // Room
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomKtx)

    // Paging
    implementation(Dependencies.pagingRuntime)
    implementation(Dependencies.pagingCommon)
}