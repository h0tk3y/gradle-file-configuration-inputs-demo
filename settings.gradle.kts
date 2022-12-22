pluginManagement {
    includeBuild("plugins")
    plugins {
        id("com.example.fileinputs.java")
        id("com.example.fileinputs.groovy")
        id("com.example.fileinputs.kotlin")
    }
}

include("app")

rootProject.name = "configuration-input-detection-demo"