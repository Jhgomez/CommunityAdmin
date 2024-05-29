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

kotlin {
    sourceSets.androidMain.dependencies {
        implementation(libs.sqldelight.android.driver)
    }

    // or iosMain, windowsMain, etc.
    sourceSets.iosMain.dependencies {
        implementation(libs.sqldelight.native.driver)
    }
}
