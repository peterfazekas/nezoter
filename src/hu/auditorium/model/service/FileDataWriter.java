package hu.auditorium.model.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileDataWriter implements DataWriter {

    private final String output;

    public FileDataWriter(String output) {
        this.output = output;
    }

    @Override
    public void printAll(List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(output))) {
            lines.forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
