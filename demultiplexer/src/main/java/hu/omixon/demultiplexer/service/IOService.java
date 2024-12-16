package hu.omixon.demultiplexer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.function.Function;

public class IOService {

    public <T> List<T> readFileLineByLine(String filePath, Function<String, T> converter) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .filter(line -> !line.isEmpty())
                    .map(converter)
                    .toList();
        }
    }

    public JsonNode readJsonFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(new File(filePath));
    }

    public void writeFile(String filename, String content) throws IOException {
        Path filePath = Path.of(filename);
        // Create the file if it doesn't exist
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        // Write content to the file, overwriting if it already exists
        Files.writeString(filePath, content, StandardOpenOption.TRUNCATE_EXISTING);
    }

}
