object Dependencies {
    val coreCtx = "androidx.core:core-ktx:${Versions.coreCtx}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val material = "com.google.android.material:material:${Versions.material}"
    val jUnit = "junit:junit:${Versions.jUnit}"
    val jUnitExt = "androidx.test.ext:junit:${Versions.jUnitExt}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltAndroid}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroid}"
    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    val pagingRuntime = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
    val pagingCommon = "androidx.paging:paging-common-ktx:${Versions.paging}"
    val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLoggingInterceptor}"

    object ProjectConfig {
        val gradleTools = "com.android.tools.build:gradle:${Versions.ProjectConfig.gradle}"
        val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.ProjectConfig.hiltGradlePlugin}"
        val safeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.ProjectConfig.safeArgsGradlePlugin}"
    }

    object Compose {
        val runtime = "androidx.compose.runtime:runtime:${Versions.Compose.main}"
        val ui = "androidx.compose.ui:ui:${Versions.Compose.main}"
        val material = "androidx.compose.material:material:${Versions.Compose.main}"
        val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.Compose.main}"
        val themeAdapter = "com.google.android.material:compose-theme-adapter:${Versions.Compose.main}"
        val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.Compose.constraintLayout}"
        val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.viewModel}"
        val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.Compose.hiltNavigation}"
        val paging = "androidx.paging:paging-compose:${Versions.Compose.paging}"
        val coil = "io.coil-kt:coil-compose:${Versions.Compose.coil}"
    }
}