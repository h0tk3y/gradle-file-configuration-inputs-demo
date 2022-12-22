pluginManagement { 
    repositories { 
        mavenCentral()
        gradlePluginPortal()
    }
    plugins { 
        kotlin("jvm").version("1.7.22")
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