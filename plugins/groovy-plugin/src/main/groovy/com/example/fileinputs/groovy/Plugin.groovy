package com.example.fileinputs.groovy

import com.example.buildinputs.Util
import org.gradle.api.Project

@SuppressWarnings('unused')
class Plugin implements org.gradle.api.Plugin<Project> {

    @Override
    void apply(Project target) {
        // Read file content at configuration time
        Util.buildInputFileContent(target, GROOVY, "property").text
        Util.buildInputFileContent(target, GROOVY, "get-default").getText()
        Util.buildInputFileContent(target, GROOVY, "get-utf-16").getText("UTF-16")

        // Check for file entry presence at configuration time
        Util.buildInputFileEntry(target, GROOVY, "exists").exists()
        Util.buildInputFileEntry(target, GROOVY, "is-file").isFile()
        Util.buildInputFileEntry(target, GROOVY, "is-directory").isDirectory()

        // Read directory entries at configuration time
        Util.buildInputDirectoryContent(target, GROOVY, "list-files").listFiles()
        Util.buildInputDirectoryContent(target, GROOVY, "list-files-filefilter")
                .listFiles((FileFilter) ((File file) -> file.getName().startsWith("file")))
        Util.buildInputDirectoryContent(target, GROOVY, "list-files-filenamefilter")
                .listFiles((FilenameFilter) (File file, String filename) -> filename.startsWith("filename"))
    }

    private static final String GROOVY = "groovy"
}
