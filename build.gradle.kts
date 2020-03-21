plugins {
    java
    kotlin("jvm") version "1.3.70"
    kotlin("kapt") version "1.3.70"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "com.panomc.plugins"
version = "1.0"

val vertxVersion = "3.8.5"

repositories {
    jcenter()
    mavenCentral()

    maven("https://oss.sonatype.org/content/repositories/iovertx-3720/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // spigot
    compileOnly("org.spigotmc:spigot-api:1.14.1-R0.1-SNAPSHOT")

    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-web-client:$vertxVersion")

    // bungeecord
    compileOnly("net.md-5:bungeecord-api:1.14-SNAPSHOT")

    // dagger 2x
    implementation("com.google.dagger:dagger:2.22.1")
    kapt("com.google.dagger:dagger-compiler:2.22.1")

    implementation("com.github.pwittchen.kirai:library:1.4.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}


tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    register("copyJar") {
        doLast {
            copy {
                from(shadowJar.get().archiveFile.get().asFile.absolutePath)
                into("../minecraft test servers/Spigot/plugins")
            }

            copy {
                from(shadowJar.get().archiveFile.get().asFile.absolutePath)
                into("../minecraft test servers/Bungeecord/plugins")
            }
        }

        dependsOn(shadowJar)
    }

    register("buildPanoCore") {
        dependsOn("build")

        dependsOn("copyJar")
    }

    build {
        dependsOn("copyJar")
    }
}
