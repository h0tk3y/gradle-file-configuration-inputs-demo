plugins {
    `java-gradle-plugin`
    id("com.example.jarsigning")
}

dependencies {
    implementation(projects.shared)
}

gradlePlugin {
    plugins { 
        create("com.example.fileinputs.java") {
            id = "com.example.fileinputs.java"
            implementationClass = "com.example.fileinputs.java.Plugin"
        }
    }
}