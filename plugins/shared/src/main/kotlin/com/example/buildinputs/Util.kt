package com.example.buildinputs

import org.gradle.api.Project
import org.gradle.api.file.Directory
import java.io.File

object Util {
    const val BUILD_INPUTS_DIR = "build-inputs"
    const val BUILD_INPUT_FILE_CONTENT_SUFFIX = "build-input-file-content.txt"
    const val BUILD_INPUT_FILE_ENTRY_SUFFIX = "build-input-file-entry"
    const val BUILD_INPUT_FILE_DIRECTORY_CONTENT_SUFFIX = "build-input-directory-content"

    @JvmStatic
    @JvmOverloads
    fun buildInputFileContent(project: Project, language: String, id: String? = null): File =
        languageSpecificBuildInputsDirectory(project, language)
            .file(fileName(language, id, BUILD_INPUT_FILE_CONTENT_SUFFIX)).asFile

    @JvmStatic
    @JvmOverloads
    fun buildInputFileEntry(project: Project, language: String, id: String? = null): File =
        languageSpecificBuildInputsDirectory(project, language)
            .file(fileName(language, id, BUILD_INPUT_FILE_ENTRY_SUFFIX)).asFile

    @JvmStatic
    @JvmOverloads
    fun buildInputDirectoryContent(project: Project, language: String, id: String? = null): File =
        languageSpecificBuildInputsDirectory(project, language)
            .file(fileName(language, id, BUILD_INPUT_FILE_DIRECTORY_CONTENT_SUFFIX)).asFile
    
    private fun fileName(language: String, id: String?, suffix: String): String =
        listOfNotNull(language, id, suffix).joinToString("-")

    private fun languageSpecificBuildInputsDirectory(project: Project, language: String): Directory =
        project.layout.projectDirectory
            .dir(BUILD_INPUTS_DIR)
            .dir(language)
}