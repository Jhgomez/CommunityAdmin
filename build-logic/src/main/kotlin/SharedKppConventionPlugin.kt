import com.android.build.gradle.LibraryExtension
import okik.tech.community.admin.configureKotlinAndroid
import okik.tech.community.admin.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinMultiplatformPlugin

class SharedKppConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

                testOptions.unitTests.isIncludeAndroidResources = true
            }


            extensions.configure<KotlinMultiplatformExtension> {
                listOf(
                    iosX64(),
                    iosArm64(),
                    iosSimulatorArm64()
                ).forEach {
                    it.binaries.framework {
                        baseName = "CommunityAdmin"
                    }
                }

                androidTarget {
                    compilations.all {
                        kotlinOptions {
                            jvmTarget = JavaVersion.VERSION_17.toString()
                        }
                    }
                }

                sourceSets.commonMain {
                    dependencies {

                    }
                }

                sourceSets.androidMain {
                    dependencies {

                    }
                }

                sourceSets.iosMain {
                    sourceSets.getByName("iosX64Main").dependsOn(this)
                    sourceSets.getByName("iosArm64Main").dependsOn(this)
                    sourceSets.getByName("iosSimulatorArm64Main").dependsOn(this)

                    dependencies {

                    }
                }
            }
        }
    }
}