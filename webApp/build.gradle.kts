@file:OptIn(ExperimentalWasmDsl::class, ExperimentalKotlinGradlePluginApi::class)

import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.util.Properties

private val ghToken: String by lazy {
    val fromGradle = providers.gradleProperty("GH_TOKEN").orNull
    val fromLocal = rootProject.file("local.properties").let { f ->
        if (f.exists()) Properties().apply { f.inputStream().use { load(it) } }.getProperty("GH_TOKEN") else null
    }
    val fromEnv = System.getenv("GH_TOKEN")
    val v = listOfNotNull(fromGradle, fromLocal, fromEnv).firstOrNull().orEmpty()
    if (v.isBlank()) throw GradleException("GH_TOKEN is empty")
    v
}

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    js {
        outputModuleName = "webApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static(project.rootDir.path)
                    static(project.projectDir.path)
                }
            }
        }
        binaries.executable()
        useEsModules()
    }
    wasmJs {
        outputModuleName = "webApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static(project.rootDir.path)
                    static(project.projectDir.path)
                }
            }
        }
        binaries.executable()
    }
    dependencies {
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.serialization.kotlinx.json)
    }
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

compose {
    resources {
        publicResClass = true
        generateResClass = always
    }
}

buildkonfig {
    packageName = "org.michaelbel.mobiledevemoji"
    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "GH_TOKEN", ghToken)
    }
}

tasks.named("jsBrowserDevelopmentRun") {
    doFirst {
        val devServer = mapOf("allowedHosts" to "all")
        project.extensions.extraProperties["webpack.devServer"] = devServer
    }
}
