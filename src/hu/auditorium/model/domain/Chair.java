package hu.auditorium.model.domain;

public class Chair {

    private final Position position;
    private final Availability availability;
    private final Category category;

    public Chair(Position position, Availability availability, Category category) {
        this.position = position;
        this.availability = availability;
        this.category = category;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isOccupied() {
        return Availability.OCCUPIED.equals(availability);
    }

    public int getCategoryId() {
        return category.getId();
    }

    public int getCategoryPrice() {
        return category.getPrice();
    }

    @Override
    public String toString() {
        return isOccupied() ? Availability.OCCUPIED.getId() : String.valueOf(category.getId());
    }
}
