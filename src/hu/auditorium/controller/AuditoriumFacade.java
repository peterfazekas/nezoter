package hu.auditorium.controller;

import hu.auditorium.controller.service.AuditoriumService;
import hu.auditorium.controller.service.CategoryService;
import hu.auditorium.controller.service.ChairService;
import hu.auditorium.model.domain.Chair;
import hu.auditorium.model.domain.Position;
import hu.auditorium.model.service.DataWriter;
import hu.auditorium.view.MessageKey;

import java.text.NumberFormat;
import java.util.List;

public class AuditoriumFacade {

    private final DataWriter dataWriter;
    private final AuditoriumService auditoriumService;
    private final CategoryService categoryService;
    private final ChairService chairService;
    private final List<Chair> chairs;

    public AuditoriumFacade(DataWriter dataWriter, AuditoriumService auditoriumService, CategoryService categoryService, ChairService chairService) {
        this.dataWriter = dataWriter;
        this.auditoriumService = auditoriumService;
        this.categoryService = categoryService;
        this.chairService = chairService;
        this.chairs = auditoriumService.getChairs();
    }

    public String getCertainChairStatus(Position position) {
        return findByPosition(position).isOccupied() ? MessageKey.CHAIR_IS_OCCUPIED : MessageKey.CHAIR_IS_AVAILABLE;
    }

    public String getAuditoriumStatistic() {
        long occupiedSeats = getOccupiedChairsNumber();
        long percent = occupiedSeats * 100 / chairs.size();
        return String.format(MessageKey.AUDITORIUM_STATISTIC_PATTERN, occupiedSeats, percent);
    }

    public String getMostPopularCategory() {
        return String.format(MessageKey.MOST_POPULAR_CATEGORY_PATTERN, categoryService.getMostPopularCategory());
    }

    public String getCurrentIncome() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(MessageKey.LOCALE_HU);
        return String.format(MessageKey.CURRENT_INCOME_PATTERN, numberFormat.format(calculateCurrentIncome()));
    }

    public String getUniqueAvailableChairCount() {
        return String.format(MessageKey.UNIQUE_AVAILABLE_CHAIR_PATTERN, countUniqueAvailableChairs());
    }

    public String printAuditoriumMap() {
        dataWriter.printAll(auditoriumService.getChairList());
        return MessageKey.AUDITORIUM_MAP_PATTERN;
    }

    private long countUniqueAvailableChairs() {
        return chairs.stream()
                .map(Chair::getPosition)
                .filter(chairService::isUniqueAvailableChair)
                .count();
    }

    private Chair findByPosition(Position position) {
        return chairs.stream()
                .filter(i -> i.getPosition().equals(position))
                .findAny()
                .get();
    }

    private long getOccupiedChairsNumber() {
        return chairs.stream()
                .filter(Chair::isOccupied)
                .count();
    }

    private int calculateCurrentIncome() {
        return chairs.stream().filter(Chair::isOccupied)
                .mapToInt(Chair::getCategoryPrice)
                .sum();
    }

}
