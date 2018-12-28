package hu.auditorium.controller.service;

import hu.auditorium.model.domain.Chair;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CategoryService {

    private final List<Chair> chairs;

    public CategoryService(List<Chair> chairs) {
        this.chairs = chairs;
    }

    public int getMostPopularCategory() {
        return createCategoryMap().entrySet().stream()
                .filter(byMaximumValue())
                .map(Entry::getKey)
                .findAny()
                .get();
    }

    private Predicate<Entry<Integer, Long>> byMaximumValue() {
        return i -> i.getValue() == getMaxValue();
    }

    private long getMaxValue() {
        return createCategoryMap().entrySet().stream()
                .mapToLong(Entry::getValue)
                .max()
                .getAsLong();
    }

    private Map<Integer, Long> createCategoryMap() {
        return chairs.stream()
                .collect(Collectors.groupingBy(Chair::getCategoryId, Collectors.counting()));
    }

}
