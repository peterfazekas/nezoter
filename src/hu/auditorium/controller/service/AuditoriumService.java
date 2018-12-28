package hu.auditorium.controller.service;

import hu.auditorium.model.domain.Chair;
import hu.auditorium.model.domain.Position;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AuditoriumService {

    private final List<Chair> chairs;

    public AuditoriumService(List<Chair> chairs) {
        this.chairs = chairs;
    }

    public List<Chair> getChairs() {
        return chairs;
    }

    public List<String> getChairList() {
        String chairSequence = transform();
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        return IntStream.range(0, rowCount)
                .mapToObj(i -> chairSequence.substring(i * columnCount, (i + 1) * columnCount))
                .collect(Collectors.toList());
    }

    private String transform() {
        return chairs.stream()
                .map(Chair::toString)
                .collect(Collectors.joining());
    }

    private int getRowCount() {
        return chairs.stream()
                .map(Chair::getPosition)
                .mapToInt(Position::getRow)
                .max()
                .getAsInt();
    }

    private int getColumnCount() {
        return chairs.stream()
                .map(Chair::getPosition)
                .mapToInt(Position::getColumn)
                .max()
                .getAsInt();
    }

}
