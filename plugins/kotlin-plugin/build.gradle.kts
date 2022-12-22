plugins {
    kotlin("jvm")
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.example.jarsigning")
}

dependencies {
    implementation(projects.shared)
}

gradlePlugin {
    plugins {
        create("com.example.fileinputs.kotlin") {
            id = "com.example.fileinputs.kotlin"
            implementationClass = "com.example.fileinputs.kotlin.Plugin"
        }
    }
}