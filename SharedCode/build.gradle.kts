import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
}


val ktor_version = "1.3.0"
val kotlin_version = "1.3.61"
val klock_version = "1.4.0"
val coroutines_version = "1.3.3"
val sqldelight_version = "1.2.2"
val serialization_version = "0.14.0"


repositories {
    maven("https://dl.bintray.com/kotlin/kotlinx")
    maven("https://dl.bintray.com/kotlin/ktor")
    maven("https://dl.bintray.com/soywiz/soywiz")
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
}


kotlin {
    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "SharedCode"
            }
        }
    }

    jvm("android")

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
    }

    sourceSets["commonMain"].dependencies {
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutines_version")
        implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization_version")
        implementation ("io.ktor:ktor-client-core:$ktor_version")
        implementation("io.ktor:ktor-client-cio:$ktor_version")
        implementation ("io.ktor:ktor-client-auth:$ktor_version")
        implementation ("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    }

    sourceSets["androidMain"].dependencies {
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
        implementation ("com.squareup.sqldelight:runtime-jvm:$sqldelight_version")
        implementation ("io.ktor:ktor-client-android:$ktor_version")
        implementation("io.ktor:ktor-client-cio:$ktor_version")
        implementation ("io.ktor:ktor-client-core-jvm:$ktor_version")
        implementation ("io.ktor:ktor-client-auth-jvm:$ktor_version")
        implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serialization_version")
    }

    sourceSets["iosMain"].dependencies {
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutines_version")
        implementation ("com.squareup.sqldelight:ios-driver:$sqldelight_version")
        implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serialization_version")
        implementation ("io.ktor:ktor-client-ios:$ktor_version")
        implementation("io.ktor:ktor-client-cio:$ktor_version")
    }

//    sourceSets["iosArm64Main"].dependencies {
//        implementation ("io.ktor:ktor-client-ios-iosarm64:$ktor_version")
//    }
//
//    sourceSets["iosX64Main"].dependencies {
//        implementation ("io.ktor:ktor-client-ios-iosx64:$ktor_version")
//    }


}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText(
            "#!/bin/bash\n"
                    + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                    + "cd '${rootProject.rootDir}'\n"
                    + "./gradlew \$@\n"
        )
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)