package com.example.fileinputs.kotlin

import com.example.buildinputs.Util.buildInputDirectoryContent
import com.example.buildinputs.Util.buildInputFileContent
import com.example.buildinputs.Util.buildInputFileEntry
import org.gradle.api.Project
import java.io.File
import java.nio.charset.StandardCharsets
import org.gradle.api.Plugin as GradlePlugin

@Suppress("unused")
class Plugin : GradlePlugin<Project> {
    override fun apply(target: Project) {
        // Read file content at configuration time
        buildInputFileContent(target, KOTLIN, "default-charset").readText()
        buildInputFileContent(target, KOTLIN, "utf-16").readText(StandardCharsets.UTF_16)

        // Check for file entry presence at configuration time
        buildInputFileEntry(target, KOTLIN, "exists").exists()
        buildInputFileEntry(target, KOTLIN, "is-file").isFile
        buildInputFileEntry(target, KOTLIN, "is-directory").isDirectory

        // Read directory entries at configuration time
        buildInputDirectoryContent(target, KOTLIN, "list-files").listFiles()
        buildInputDirectoryContent(target, KOTLIN, "list-files-filefilter")
            .listFiles { file: File -> file.name.startsWith("file") }
        buildInputDirectoryContent(target, KOTLIN, "list-files-filenamefilter")
            .listFiles { _: File, filename: String -> filename.startsWith("filename") }
    }

    companion object {
        private const val KOTLIN = "kotlin"
    }
}