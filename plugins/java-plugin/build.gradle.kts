plugins {
    `java-gradle-plugin`
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