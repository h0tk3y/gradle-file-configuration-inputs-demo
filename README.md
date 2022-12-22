### Configuration cache input detection demo

This sample project demonstrates new `File`-related APIs covered by configuration build input detection in Gradle.

### New APIs coverage

The following API calls from the build scripts or plugin code 
(as well as plugin dependencies, except for Kotlin and Groovy runtimes) are now tracked as build configuration inputs:

* All JVM languages:
  * `File.exists()`, `File.isFile()`, `File.isDirectory()`
    * Detect that the file system entry is created, deleted, or changes its kind (file ↔ directory) 
  * `File.listFiles(...)`
    * Detect the changes in the directory's child entries list (the set of names)
  * `Files.readString(...)` (Java 11+)
    * Detect the file content changes
* Kotlin
  * `File.readText(...)` extension
    * Detect the file content changes
* Groovy
  * `File.text` property, `File.getText(...)`
    * Detect the file content changes

### Repository content

This repository includes the `plugins` in three languages (`java-plugin`, `kotlin-plugin`, `groovy-plugin`)
that access the `File`-related APIs at the configuration time.

The three plugins are applied to the `app` subproject, and they access the files in the
`app/build-inputs` directory. Their configuration input files are grouped by the plugin (language).
For example, the `kotlin-plugin` reads configuration input files in `app/build-inputs/kotlin`.

The configuration inputs contain files as well as directories whose contents are used as inputs.

### How to run

Run the build for the first time (or after cleaning the configuration cache with `rm -rf .gradle/configuration-cache`):

> 🔴 **TODO** Update the Gradle wrapper in this repository once it's built 

```
./gradlew :app:help
```

> To sign the plugin JARs and test the input detection with signed JARs, add `-Pjarsigning.enabled=true`

In the outputs, find the lines:

```
...
0 problems were found storing the configuration cache.

See the complete report at file://URL_HERE/configuration-cache-report.html
```

In the report, find the tracked inputs under the _Build configuration inputs_ tab.

To see how the configuration cache reacts to the changes in the build inputs, manipulate
the files in the `app/build-inputs` directory. For example, removing `app/build-inputs/kotlin/kotlin-is-file-build-input-file-entry`
will result in the following message in the next build:

```
Calculating task graph as configuration cache cannot be reused because the file system entry 'app/build-inputs/kotlin/kotlin-is-file-build-input-file-entry' has been removed.
```

Modifying the children files of the `app/build-inputs/kotlin/kotlin-list-files-build-input-directory-content` directory
will result in the following reason to recalculate the task graph:

```
Calculating task graph as configuration cache cannot be reused because the content of directory 'app/build-inputs/kotlin/kotlin-list-files-build-input-directory-content' has changed.
```

### Detecting build inputs in the signed JARs
By default, detecting build inputs in the signed JARs is enabled. You can verify this by running:
```
./gradlew -q -Pjarsigning.enabled=true -DCI=true :app:printSignatures
./gradlew -q -Pjarsigning.enabled=true -DCI=false :app:printSignatures
```
You can see that the change of the system property invalidates the configuration cache.

The new logic can be turned off by adding `-Dorg.gradle.internal.instrumentation.agent=false` to the command line (or by modifying the `gradle.properties` file, where this feature is enabled).
