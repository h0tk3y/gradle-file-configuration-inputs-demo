plugins {
    `kotlin-dsl`
}

gradlePlugin.plugins.create("com.example.jarsigning") {
    id = "com.example.jarsigning"
    implementationClass = "com.example.jarsigning.JarSigningPlugin"
}