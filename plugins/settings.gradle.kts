pluginManagement { 
    includeBuild("jar-signing")
    repositories { 
        mavenCentral()
        gradlePluginPortal()
    }
    plugins { 
        kotlin("jvm").version("1.7.22")
        id("com.example.jarsigning")
    }
}

dependencyResolutionManagement {
    repositories { 
        mavenCentral()
    }
}

include("shared")
include("java-plugin")
include("kotlin-plugin")
include("groovy-plugin")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")