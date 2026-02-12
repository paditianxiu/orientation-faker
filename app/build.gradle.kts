plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "me.padi.orientationfaker"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "me.padi.orientationfaker"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

        androidComponents {
            onVariants(selector().all()) { variant ->
                variant.outputs.map { it as com.android.build.api.variant.impl.VariantOutputImpl }
                    .forEach { output ->
                        output.outputFileName =
                            "OrientationFaker_v${output.versionName.get()}(${variant.name}).apk"
                    }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation("top.yukonga.miuix.kmp:miuix-android:0.8.0")
    implementation("top.yukonga.miuix.kmp:miuix-icons-android:0.8.0")
    implementation("androidx.navigation3:navigation3-runtime:1.1.0-alpha03")
    implementation("top.yukonga.miuix.kmp:miuix-navigation3-ui:0.8.0")
    implementation("top.yukonga.miuix.kmp:miuix-navigation3-adaptive:0.8.0")
}