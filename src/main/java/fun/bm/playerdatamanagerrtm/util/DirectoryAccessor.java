package fun.bm.playerdatamanagerrtm.util;

import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class DirectoryAccessor {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void copyDirectory(File sourceDir, File destDir) throws IOException {
        Path sourcePath = sourceDir.toPath();
        Path destPath = destDir.toPath();

        Files.walkFileTree(sourcePath, new SimpleFileVisitor<>() {
            @NotNull
            public FileVisitResult preVisitDirectory(@NotNull Path dir, @NotNull BasicFileAttributes attrs) throws IOException {
                Path targetDir = destPath.resolve(sourcePath.relativize(dir));
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }

            @NotNull

            public FileVisitResult visitFile(@NotNull Path file, @NotNull BasicFileAttributes attrs) throws IOException {
                Files.copy(file, destPath.resolve(sourcePath.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }

    public static void initFile(File file) {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            try {
                if (parentDir.mkdirs()) {
                    LOGGER.info("Created directory: {}", parentDir.getAbsolutePath());
                } else {
                    LOGGER.warn("Failed to create directory: {}", parentDir.getAbsolutePath());
                }
            } catch (Exception e) {
                LOGGER.warn("Error creating directory {}: {}", parentDir.getAbsolutePath(), e.getMessage());
            }
        }

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    LOGGER.info("Created file: {}", file.getAbsolutePath());
                } else {
                    LOGGER.warn("Failed to create {}", file);
                }
            } catch (Exception e) {
                LOGGER.warn("Failed to create {}:", file, e);
            }
        }
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                LOGGER.warn("Failed to delete {}:", file, e);
            }
        }
    }
}
