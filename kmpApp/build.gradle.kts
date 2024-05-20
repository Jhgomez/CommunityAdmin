import dev.icerock.gradle.MRVisibility

plugins {
    alias(libs.plugins.okik.kpp.application)
}

private val packageName = "okik.tech.community.admin"
private val resourcesPackageName = "okik.tech.community.admin.resources"

android {
    namespace = packageName

//    defaultConfig {
//        applicationId = "okik.tech.community.admin"
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
}

kotlin {
    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        podfile = project.file("../iosApp/Podfile")

        framework {
            export(libs.icerock.moko.resources.compose)
            export(libs.icerock.moko.resources)
        }
    }

    androidTarget {
//        compilations.all {
//            kotlinOptions {
//                jvmTarget = JavaVersion.VERSION_11.toString()
//            }
//        }

        dependencies {
            implementation("androidx.core:core-ktx:1.9.0")
            implementation("androidx.appcompat:appcompat:1.6.1")
//            implementation("com.google.android.material:material:1.10.0")
//            implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//            implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
//            implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
//            implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
            implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
//            testImplementation("junit:junit:4.13.2")
//            androidTestImplementation("androidx.test.ext:junit:1.1.5")
//            androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = resourcesPackageName
    multiplatformResourcesClassName = "SharedRes"
    multiplatformResourcesVisibility = MRVisibility.Public
}