import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

//Remember this configurations doesn't affect or depend on what is running on the user's device
//This will help the plugins in build-logic target the same JDK needed to build the project
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

gradlePlugin {
    plugins {
        register("gitTagsVersionCode") {
            id = "okik.android.versioning"
            implementationClass = "VersioningProviderConventionPlugin"
        }
        register("kppAplication") {
            id = "okik.kpp.app"
            implementationClass = "KppAppConventionPlugin"
        }
        register("kppShared") {
            id = "okik.kpp.shared"
            implementationClass = "SharedKppConventionPlugin"
        }
    }
}

dependencies {
    compileOnly(libs.kotlin.gradleplugin)
    compileOnly(libs.android.gradleplugin)
    compileOnly(libs.compose.multiplatform.gradleplugin)
    compileOnly(libs.multiplatform.icerock.resources.generator)
    compileOnly(libs.sqldelight.gradleplugin)
    compileOnly(libs.jetbrains.compose.compiler.graldeplugin)
}