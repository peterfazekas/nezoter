package hu.auditorium.model.service;

import hu.auditorium.model.domain.Position;

public class PositionParser {

    private final Console console;

    public PositionParser(Console console) {
        this.console = console;
    }

    public Position createPosition(String rowText, String columnText) {
        int row = console.readInt(rowText);
        int column = console.readInt(columnText);
        return new Position(row, column);
    }

}
