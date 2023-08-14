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
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileCategorizer {

    public static void main(String[] args) {
        Path sourceDirectory = Paths.get(args[0]);
        Path destinationBaseDirectory = Paths.get(args[1]);

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        try {

            Set<String> prefixes = FilePrefixLister.prefixesList(args);
            for (String pref : prefixes) {
                Files.walkFileTree(sourceDirectory, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (Files.isRegularFile(file)) {
                            String fileName = file.getFileName().toString();

                            String prefix = pref; // getPrefix(fileName);
                            if (!fileName.contains(prefix)) {
                                return FileVisitResult.CONTINUE;
                            }
                            if (prefix != null) {
                                Path destinationDirectory = destinationBaseDirectory.resolve(prefix);
                                if (!Files.exists(destinationDirectory)) {
                                    Files.createDirectories(destinationDirectory);
                                }

                                executor.execute(() -> {
                                    try {
                                        copyFile(file, destinationDirectory.resolve(fileName));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
            System.out.println("Cpying files completed successfully ");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Shut down the executor
        executor.shutdown();
    }

    private static void copyFile(Path sourceFile, Path destinationFile) throws IOException {
        System.out.println("Cpying file " + sourceFile + " to " + destinationFile);
        Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
    }
}
