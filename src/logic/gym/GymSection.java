package logic.gym;

public class GymSection {
    private String title;
    private String description;
    private int maxPeopleCapacity;

    public GymSection(String title, String description, int maxPeopleCapacity) {
        this.title = title;
        this.description = description;
        this.maxPeopleCapacity = maxPeopleCapacity;
    }

    public int getMaxPeopleCapacity() {
        return maxPeopleCapacity;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
