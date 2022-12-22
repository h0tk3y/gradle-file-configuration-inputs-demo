package com.example.fileinputs.java;

import com.example.buildinputs.Util;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@SuppressWarnings("unused")
public class Plugin implements org.gradle.api.Plugin<Project> {

    @Override
    public void apply(@NotNull Project target) {
        // Read file content at configuration time
        try {
            Files.readString(Util.buildInputFileContent(target, JAVA, "default-charset").toPath());
            Files.readString(Util.buildInputFileContent(target, JAVA, "utf-16").toPath(), StandardCharsets.UTF_16);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Check for file entry presence at configuration time
        boolean fileExists = Util.buildInputFileEntry(target, JAVA, "exists").exists();
        boolean fileIsFile = Util.buildInputFileEntry(target, JAVA, "is-file").isFile();
        boolean fileIsDirectory = Util.buildInputFileEntry(target, JAVA, "is-directory").isDirectory();

        // Read directory entries at configuration time
        File[] listFiles = Util.buildInputDirectoryContent(target, JAVA, "list-files").listFiles();
        File[] listFilesFilefilter =
                Util.buildInputDirectoryContent(target, JAVA, "list-files-filefilter")
                        .listFiles((File file) -> file.getName().startsWith("file"));
        File[] listFilesFilenameFilter =
                Util.buildInputDirectoryContent(target, JAVA, "list-files-filenamefilter")
                        .listFiles((File file, String filename) -> filename.startsWith("filename"));
    }

    private static final String JAVA = "java";
}
