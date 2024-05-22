plugins {
    alias(libs.plugins.okik.shared.kpp)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
    }
}

android {
    namespace = "okik.tech.community.admin.shared.networking"
}