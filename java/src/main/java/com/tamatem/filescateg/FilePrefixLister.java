/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tamatem.filescateg;

/**
 *
 * @author BasharOthman
 */
import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public class FilePrefixLister {

    public static Set<String> prefixesList(String[] args) {
        Path directory = Paths.get(args[0]);
        Set<String> prefixes = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {

            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    String fileName = entry.getFileName().toString();
                    String prefix = getPrefix(fileName);
                    if (prefix != null) {
                        prefixes.add(prefix);
                    }
                }
            }

            for (String prefix : prefixes) {
                System.out.println(prefix);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prefixes;
    }

    private static String getPrefix(String fileName) {

        if (fileName.contains("-")) {
            return fileName.substring(0, fileName.indexOf('-'));
        }
        return null;
    }
}
