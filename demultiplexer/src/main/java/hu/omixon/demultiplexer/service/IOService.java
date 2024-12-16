package hu.omixon.demultiplexer.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

}
