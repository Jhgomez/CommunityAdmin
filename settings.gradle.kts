pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("gradle/ca-libs.versions.toml"))
        }
    }

    // Configuration causes KMP failures when adding some configurations, check for compatibility
    // in next gradle updates
//    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CommunityAdmin"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":kmpApp")
include(":shared")
include(":shared:networking")
include(":shared:database")
include(":shared:impl")