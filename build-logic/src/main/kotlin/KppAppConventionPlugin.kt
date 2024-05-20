import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import okik.tech.community.admin.configureKotlinAndroid
import okik.tech.community.admin.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KppAppConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("okik.android.versioning")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.native.cocoapods")
                apply("org.jetbrains.compose")
                apply("dev.icerock.mobile.multiplatform-resources")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    targetSdk =
                        libs.findVersion("targetSdk").get().toString().toInt()

                    applicationId = "okik.tech.community.admin"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                configureKotlinAndroid(this)

                compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

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
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }

                testOptions.unitTests.isIncludeAndroidResources = true

                dependencies {
                    add("implementation", libs.findLibrary("androidx.core.splashscreen").get())
                }
            }

            plugins.withType(ComposePlugin::class.java) {

                extensions.configure<KotlinMultiplatformExtension> {
                    androidTarget() //this configuration could be removed, try removing it after all project is compiled
                    iosX64()
                    iosArm64()
                    iosSimulatorArm64()

                    sourceSets.commonMain {
                        dependencies {
                            implementation(libs.findLibrary("compose.foundation").get())
                            implementation(libs.findLibrary("compose.material3").get())
                            api(libs.findLibrary("icerock.moko.resources.compose").get())
                        }
                    }

                    sourceSets.androidMain {
                        dependsOn(sourceSets.getByName("commonMain"))
                        dependencies {
                            api(libs.findLibrary("activity.compose").get())
                        }
                    }

                    sourceSets.iosMain {
                        dependsOn(sourceSets.getByName("commonMain"))
                        sourceSets.getByName("iosX64Main").dependsOn(this)
                        sourceSets.getByName("iosArm64Main").dependsOn(this)
                        sourceSets.getByName("iosSimulatorArm64Main").dependsOn(this)
                    }

                    // Check if opt-in still needed in following Kotlin releases
                    // https://youtrack.jetbrains.com/issue/KT-61573
                    androidTarget().compilations.configureEach {
                        compilerOptions.configure {
                            freeCompilerArgs.add("-Xexpect-actual-classes")
                        }
                    }

                    dependencies {
                        add("commonTestImplementation", libs.findLibrary("icerock.moko.resources.test").get())
                    }
                }
            }
        }
    }
}