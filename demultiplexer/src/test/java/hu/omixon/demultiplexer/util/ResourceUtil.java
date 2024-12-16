package hu.omixon.demultiplexer.util;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@UtilityClass
public class ResourceUtil {

    public String getAsAbsolutePath(String resourcePath) {
        return "src/test/resources/" + resourcePath;
    }

    public List<Path> listDirectories(String dirPath) throws IOException {
        String absolutePath = getAsAbsolutePath(dirPath);
        Path path = Paths.get(absolutePath);

        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IllegalArgumentException("The provided path does not exist or is not a directory: " + dirPath);
        }

        try (Stream<Path> stream = Files.list(path)) {
            return stream.filter(Files::isDirectory).toList();
        }
    }


    public static List<File> listFiles(String absolutePath) throws IOException {

        Path directoryPath = Paths.get(absolutePath);

        if (!Files.exists(directoryPath) || !Files.isDirectory(directoryPath)) {
            throw new IllegalArgumentException("The provided path does not exist or is not a directory: " + directoryPath);
        }

        try (Stream<Path> paths = Files.list(directoryPath)) {
            return paths.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .toList();
        }
    }

    public static String readFileContent(File file) throws IOException {
        if (file == null || !file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("The provided File object is null, does not exist, or is not a file: " + file);
        }

        return Files.readString(file.toPath());
    }

}
