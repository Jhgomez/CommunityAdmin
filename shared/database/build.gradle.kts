plugins {
    alias(libs.plugins.okik.shared.kpp)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "okik.tech.community.admin.shared.storage"
}

sqldelight {
    databases {
        create("CADatabase") {
            packageName.set("okik.tech.community.admin.shared.database")
        }
    }
}

//kotlin {
//    sourceSets.androidMain.dependencies {
//        implementation(libs.sqldelight.android_driver)
//    }
//
//    // or iosMain, windowsMain, etc.
//    sourceSets.nativeMain.dependencies {
//        implementation(libs.sqldelight.native_driver)
//    }
//
//    sourceSets.jvmMain.dependencies {
//        implementation(libs.sqldelight.driver)
//    }
//}
