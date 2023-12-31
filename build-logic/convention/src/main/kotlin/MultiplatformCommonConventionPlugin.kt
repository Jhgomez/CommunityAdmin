import com.android.build.gradle.LibraryExtension
import okik.tech.community.admin.configureKotlinAndroid
import okik.tech.community.admin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformCommonConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.native.cocoapods")
                apply("com.android.library")
                apply("org.jetbrains.compose")
            }

            plugins.withType(ComposePlugin::class.java) {
                extensions.configure<LibraryExtension> {
                    configureKotlinAndroid(this)
                    compileSdk = libs.findVersion("compileSdk").get().toString().toInt()
                }

                extensions.configure<KotlinMultiplatformExtension> {
                    androidTarget() //this configuration could be removed, try removing it after all project is compiled

                    listOf(
                        iosX64(),
                        iosArm64(),
                        iosSimulatorArm64()
                    ).forEach { iosTarget ->
                        iosTarget.binaries.framework {
                            baseName = "shared"
                            isStatic = true
                        }
                    }

                    sourceSets.commonMain {
                        dependencies {
                            implementation(libs.findLibrary("compose.foundation").get())
                            implementation(libs.findLibrary("compose.material3").get())
                        }
                    }

                    sourceSets.androidMain {
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
                }
            }
        }
    }
}