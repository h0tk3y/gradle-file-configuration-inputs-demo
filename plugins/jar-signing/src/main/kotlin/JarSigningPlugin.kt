package com.example.jarsigning

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.jvm.toolchain.JavaLauncher
import org.gradle.jvm.toolchain.JavaToolchainService
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import javax.inject.Inject

abstract class JarSigningPlugin @Inject constructor(): Plugin<Project> {
    abstract val launcher: Property<JavaLauncher>
    
    override fun apply(target: Project) = with(target) {
        launcher.convention(run {
            val toolchain = project.extensions.getByType<JavaPluginExtension>().toolchain
            val service = project.extensions.getByType<JavaToolchainService>()
            service.launcherFor(toolchain)
        })
        
        val enabled = providers.gradleProperty("jarsigning.enabled").map(String::toBoolean)
        val keystore = project.layout.file(providers.gradleProperty("jarsigning.keystore").map { file(it) })
        val keystoreAlias = providers.gradleProperty("jarsigning.keystoreAlias")
        val keyStorePass = providers.gradleProperty("jarsigning.keyStorePass")
        val keyStoreKeyPass = providers.gradleProperty("jarsigning.keyStoreKeyPass")
        val tsa = providers.gradleProperty("jarsigning.tsa")
        
        pluginManager.withPlugin("java") {
            tasks.withType<Jar>().named("jar").configure { 
                signJar(this, enabled, keystore, keystoreAlias, keyStorePass, keyStoreKeyPass, tsa)
            }
        }
    }

    fun signJar(
        jar: Jar,
        enabled: Provider<Boolean>,
        keystore: Provider<RegularFile>,
        keystoreAlias: Provider<String>,
        keyStorePass: Provider<String>,
        keyStoreKeyPass: Provider<String>,
        tsa: Provider<String>
    ) = with(jar) {
        with(jar.inputs) {
            file(keystore.get())
            property("jarsigning-enabled", enabled.get())
            property("keystoreAlias", keystoreAlias)
            property("keystorePass", keyStorePass)
            property("keyStoreKeyPass", keyStoreKeyPass)
            property("tsa", tsa)
        }
        
        jar.doLast {
            if (enabled.get()) {
                val jarsigner = launcher.get().executablePath.asFile.parentFile.resolve("jarsigner")
                val process = ProcessBuilder().command(
                    jarsigner.absolutePath,
                    "-keystore", keystore.get().asFile.absolutePath,
                    "-storepass", keyStorePass.get(),
                    "-keypass", keyStoreKeyPass.get(),
                    "-tsa", tsa.get(),
                    "-verbose",
                    archiveFile.get().asFile.absolutePath, keystoreAlias.get()
                ).inheritIO().start()
                val exitCode = process.waitFor()
                if (exitCode != 0) {
                    println("failed to sign the JAR")
                }
            }
        }
    }
}