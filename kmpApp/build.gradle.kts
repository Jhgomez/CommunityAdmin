import dev.icerock.gradle.MRVisibility

plugins {
    alias(libs.plugins.okik.kpp.application)
}

private val packageName = "okik.tech.community.admin"
private val resourcesPackageName = "okik.tech.community.admin.resources"

android {
    namespace = packageName
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
}

multiplatformResources {
    multiplatformResourcesPackage = resourcesPackageName
    multiplatformResourcesClassName = "SharedRes"
    multiplatformResourcesVisibility = MRVisibility.Public
}