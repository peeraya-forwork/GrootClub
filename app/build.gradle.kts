plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.grootclub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.grootclub"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    viewBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    implementation("com.google.android.gms:play-services-fido:20.1.0")

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    //Hawk
    implementation ("com.orhanobut:hawk:2.0.1")

    //OkHTTP
    implementation ("com.squareup.okhttp3:okhttp:4.10.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")


    //gson converter by retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.6.4")
    //Rx java
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("io.reactivex.rxjava2:rxjava:2.2.17")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.8.1")

    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

//    implementation group: 'io.appium', name: 'java-client', version: '7.1.0'
//    implementation group: 'org.seleniumhq.selenium', name: 'selenium-java', version: '3.141.0'
//    implementation group: 'org.testng', name: 'testng', version: '7.3.0'
//    implementation("io.appium:java-client:7.1.0")
//    implementation("org.seleniumhq.selenium:selenium-java:3.141.0")
//    implementation("org.testng:testng:7.3.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}