plugins {
    groovy
    `java-gradle-plugin`
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