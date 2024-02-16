import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
    alias(libs.plugins.sqldelight)

}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.koin)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.koin)
            implementation(libs.voyager.transitions)
            implementation(libs.ktor.core)
            implementation(libs.ktor.cio)
            implementation(libs.ktor.serializationJSON)
            implementation(libs.ktor.client.resourses)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.serialization)
            implementation(libs.sqldelight.coroutines)
            implementation(libs.sqldelight.adapter.primitive)
            implementation(libs.sqldelight.driver)
            implementation(libs.paging.compose)
            implementation(libs.paging.common)
            implementation(libs.coroutines)
            implementation(libs.coil)
            implementation(libs.coilNetwork)
        }




        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.junit)
        }
    }
}

sqldelight{
    databases{
        create("Cache"){
            packageName.set("io.vladyslavv_ua.sql")
            dialect(libs.sqldelight.dialect.sqlite)
            deriveSchemaFromMigrations = true
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "io.vladyslavv_ua.mangadexdownloader"
            packageVersion = "1.0.0"
        }


//        buildTypes.release.proguard {
//            configurationFiles.from(project.file("proguard.pro"))
//        }
    }
}
