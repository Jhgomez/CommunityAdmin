plugins {
    alias(libs.plugins.okik.shared.kpp)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
        }
    }
}

android {
    namespace = "okik.tech.community.admin.shared.networking"
}