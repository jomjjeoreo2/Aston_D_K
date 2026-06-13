public class Product {
    private String name;
    private String productionDate;
    private String manufacturer;
    private String countryOfOrigin;
    private double price;
    private boolean isReservedByBuyer;

    public Product(String name, String productionDate, String manufacturer,
                   String countryOfOrigin, double price, boolean isReservedByBuyer) {
        this.name = name;
        this.productionDate = productionDate;
        this.manufacturer = manufacturer;
        this.countryOfOrigin = countryOfOrigin;
        this.price = price;
        this.isReservedByBuyer = isReservedByBuyer;
    }

    public void displayInfo() {
        System.out.println("Название: " + name);
        System.out.println("Дата производства: " + productionDate);
        System.out.println("Производитель: " + manufacturer);
        System.out.println("Страна происхождения: " + countryOfOrigin);
        System.out.println("Цена: " + price);
        System.out.println("Забронирован покупателем: " + (isReservedByBuyer ? "Да" : "Нет"));
        System.out.println("------------------------------");
    }
}
