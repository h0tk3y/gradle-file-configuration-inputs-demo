plugins {
    groovy
    `java-gradle-plugin`
    id("com.example.jarsigning")
}

dependencies {
    implementation(projects.shared)
}

gradlePlugin {
    plugins {
        create("com.example.fileinputs.groovy") {
            id = "com.example.fileinputs.groovy"
            implementationClass = "com.example.fileinputs.groovy.Plugin"
        }
    }
}