package hu.auditorium.controller.service;

import hu.auditorium.model.domain.Chair;
import hu.auditorium.model.domain.Position;

import java.util.List;
import java.util.Optional;

public class ChairService {

    private final List<Chair> chairs;

    public ChairService(List<Chair> chairs) {
        this.chairs = chairs;
    }

    public boolean isUniqueAvailableChair(Position position) {
        return isAvailable(position) && hasLeftNeighbour(position) && hasRightNeighbour(position);
    }

    private boolean isAvailable(Position position) {
        return !isOccupied(position);
    }

    private boolean hasLeftNeighbour(Position position) {
        return isOccupied(position.toLeft());
    }

    private boolean hasRightNeighbour(Position position) {
        return isOccupied(position.toRight());
    }
    private boolean isOccupied(Position position) {
        return findByPosition(position)
                .map(Chair::isOccupied)
                .orElse(true);
    }

    private Optional<Chair> findByPosition(Position position) {
        return chairs.stream().filter(i -> i.getPosition().equals(position)).findAny();
    }


}
