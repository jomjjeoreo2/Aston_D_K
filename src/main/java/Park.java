import java.util.ArrayList;
import java.util.List;

public class Park {
    private String name;
    private List<Attraction> attractions;

    public Park(String name) {
        this.name = name;
        this.attractions = new ArrayList<>();
    }

    public void addAttraction(Attraction attraction) {
        attractions.add(attraction);
    }

    public void displayAttractions() {
        System.out.println("Парк: " + name + "\nАттракционы:");
        for (Attraction attraction : attractions) {
            attraction.displayInfo();
            System.out.println("------------------------------");
        }
    }

    class Attraction {
        private String name;
        private String operatingHours;
        private double cost;

        Attraction(String name, String operatingHours, double cost) {
            this.name = name;
            this.operatingHours = operatingHours;
            this.cost = cost;
        }

        void displayInfo() {
            System.out.println("Аттракцион: " + name);
            System.out.println("Время работы: " + operatingHours);
            System.out.println("Стоимость: " + cost);
        }
    }
}