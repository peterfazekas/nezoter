package hu.auditorium.model.service;

import hu.auditorium.model.domain.Availability;
import hu.auditorium.model.domain.Category;
import hu.auditorium.model.domain.Chair;
import hu.auditorium.model.domain.Position;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChairParser implements DataParser<Chair> {

    private final ListTransformer listTransformer;

    public ChairParser(ListTransformer listTransformer) {
        this.listTransformer = listTransformer;
    }

    @Override
    public List<Chair> parse(List<String> availabilityList, List<String> categoryList) {
        Position maxPosition = new Position(listTransformer.getRow(availabilityList), listTransformer.getColumn(availabilityList));
        String availabilities = listTransformer.transform(availabilityList);
        String categories = listTransformer.transform(categoryList);
        return IntStream.range(0, availabilities.length())
                .mapToObj(i -> createChair(i, availabilities, categories, maxPosition))
                .collect(Collectors.toList());
    }

    private Chair createChair(int index, String availabilities, String categories, Position maxPosition) {
        Position position = Position.toPosition(index, maxPosition);
        Availability availability = getAvailability(index, availabilities);
        Category category = Category.getById(getCategoryId(index, categories));
        return new Chair(position, availability, category);
    }

    private Availability getAvailability(int index, String availabilities) {
        return Availability.getById(String.valueOf(availabilities.charAt(index)).toLowerCase());
    }

    private int getCategoryId(int index, String categories) {
        return Integer.parseInt(String.valueOf(categories.charAt(index)));
    }

}
