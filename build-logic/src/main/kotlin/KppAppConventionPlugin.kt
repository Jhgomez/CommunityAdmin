import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import okik.tech.community.admin.configureKotlinAndroid
import okik.tech.community.admin.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
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
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                testOptions.unitTests.isIncludeAndroidResources = true

                dependencies {
                    add("implementation", libs.findLibrary("androidx.core.splashscreen").get())
                }
            }

            plugins.withType(ComposePlugin::class.java) {

                extensions.configure<KotlinMultiplatformExtension> {
                    androidTarget {
                        compilations.all {

                            compilerOptions.configure {
                                freeCompilerArgs.add("-Xexpect-actual-classes")
                            }
                        }
                    }
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

                            implementation(libs.findLibrary("kotlinx.coroutines.android").get())
                            implementation(libs.findLibrary("androidx.core.ktx").get())
                            implementation(libs.findLibrary("androidx.appcompat").get())
                            implementation(libs.findLibrary("androidx.navigation.ktx").get())

//            implementation("com.google.android.material:material:1.10.0")
//            implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//            implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
//            implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
//            implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")

//            testImplementation("junit:junit:4.13.2")
//            androidTestImplementation("androidx.test.ext:junit:1.1.5")
//            androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
                        }
                    }

                    sourceSets.iosMain {
                        dependsOn(sourceSets.getByName("commonMain"))
                        sourceSets.getByName("iosX64Main").dependsOn(this)
                        sourceSets.getByName("iosArm64Main").dependsOn(this)
                        sourceSets.getByName("iosSimulatorArm64Main").dependsOn(this)
                    }

                    dependencies {
                        add("commonTestImplementation", libs.findLibrary("icerock.moko.resources.test").get())
                    }
                }
            }
        }
    }
}