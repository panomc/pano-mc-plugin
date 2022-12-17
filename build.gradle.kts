val vertxVersion = "4.3.6"

plugins {
    java
    kotlin("jvm") version "1.7.21"
    kotlin("kapt") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.panomc.plugins"
version = "1.0"

val buildType = "alpha"
val timeStamp: String by project
val fullVersion = if (project.hasProperty("timeStamp")) "$version-$buildType-$timeStamp" else "$version-$buildType"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://oss.sonatype.org/content/repositories/iovertx-3720/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // spigot
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")

    // bungeecord
    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")

    // paper spigot
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")

    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-web-client:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")
    implementation("io.vertx:vertx-config:$vertxVersion")
    implementation("io.vertx:vertx-config-hocon:$vertxVersion")
    implementation("io.vertx:vertx-json-schema:$vertxVersion")

    // dagger 2x
    implementation("com.google.dagger:dagger:2.44.2")
    kapt("com.google.dagger:dagger-compiler:2.44.2")

    implementation("com.github.pwittchen.kirai:library:1.4.1")

    implementation("org.springframework:spring-context:5.3.24")

    implementation("io.vertx:vertx-rx-java3:4.3.6")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    register("copyJarToRoot") {
        doLast {
            copy {
                from(shadowJar.get().archiveFile.get().asFile.absolutePath)
                into("./")
            }
        }

        dependsOn(shadowJar)
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

    build {
        dependsOn("copyJarToRoot")
    }

    register("buildDev") {
        dependsOn("build")
    }

    // This task builds and copys jar into server folders for test
    register("buildPluginDev") {
        dependsOn("copyJar")
        dependsOn("build")
    }

    shadowJar {
        manifest {
            val attrMap = mutableMapOf<String, String>()

            if (
                project.gradle.startParameter.taskNames.contains("buildDev") ||
                project.gradle.startParameter.taskNames.contains("buildPluginDev")
            ) {
                attrMap["MODE"] = "DEVELOPMENT"
            }

            attrMap["VERSION"] = fullVersion
            attrMap["BUILD_TYPE"] = buildType

            attributes(attrMap)
        }

        if (project.hasProperty("timeStamp")) {
            archiveFileName.set("Pano-MC-plugin-${timeStamp}.jar")
        } else {
            archiveFileName.set("Pano-MC-plugin.jar")
        }
    }
}
