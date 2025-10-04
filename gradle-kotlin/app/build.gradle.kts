import com.diffplug.spotless.kotlin.KtfmtStep

plugins {
    application

    alias(libs.plugins.kotlin.jvm)

    alias(libs.plugins.versions)
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
    alias(libs.plugins.buildtools)
}

group = "tech.harmless.TODO"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    implementation(libs.jspecify)
    implementation(libs.tinylog.api)
    implementation(libs.tinylog.impl)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}

java {
    withSourcesJar()
    // withJavadocJar()
    toolchain { languageVersion = JavaLanguageVersion.of(24) }
}

tasks.compileJava {
    dependsOn("copyFiles")
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:all")
}

tasks.jar { dependsOn("copyDepJars") }

application { mainClass = "tech.harmless.TODO.KAppKt" }

tasks.register<Copy>("copyFiles") {
    mustRunAfter(tasks.processResources)
    from("$rootDir/LICENSE")
    into(layout.buildDirectory.dir("resources/main/META-INF/tech/harmless/TODO").get())
}

tasks.register<Copy>("copyDepJars") {
    shouldRunAfter(tasks.jar)
    from(configurations.runtimeClasspath)
    into(layout.buildDirectory.dir("libs/libs").get())
}

tasks.register<Jar>("javadocJar") {
    dependsOn(tasks.dokkaGenerateHtml)
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("dokka/html").get())
}

tasks.named<Test>("test") { useJUnitPlatform() }

spotless {
    kotlin {
        licenseHeaderFile("$rootDir/other/license-header")
        ktfmt("0.58").kotlinlangStyle().configure {
            it.setRemoveUnusedImports(true)
            it.setTrailingCommaManagementStrategy(
                KtfmtStep.TrailingCommaManagementStrategy.COMPLETE
            )
        }
    }
    kotlinGradle {
        ktfmt("0.58").kotlinlangStyle().configure {
            it.setRemoveUnusedImports(true)
            it.setTrailingCommaManagementStrategy(
                KtfmtStep.TrailingCommaManagementStrategy.COMPLETE
            )
        }
    }
    flexmark {
        target("**/*.md")
        targetExclude("build/**", "tmp/**")
        flexmark()
    }
    json {
        target("**/*.json")
        targetExclude("build/**", "tmp/**")
        gson()
    }
}

graalvmNative { binaries.all { buildArgs.add("--enable-sbom") } }
