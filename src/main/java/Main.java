public class Main {
    public static void main(String[] args) {
        Product[] productsArray = new Product[5];

        productsArray[0] = new Product("Samsung S25 Ultra", "01.02.2025", "Samsung Corp.", "Korea", 5599, true);
        productsArray[1] = new Product("iPhone 17 Pro", "15.03.2025", "Apple Inc.", "USA", 6499, false);
        productsArray[2] = new Product("Xiaomi 14 Ultra", "10.01.2025", "Xiaomi Group", "China", 3999, true);
        productsArray[3] = new Product("Sony WH-1000XM6", "20.04.2025", "Sony Corp.", "Japan", 2999, false);
        productsArray[4] = new Product("Dyson V15 Absolute", "05.03.2025", "Dyson Ltd.", "UK", 4499, true);

        for (Product product : productsArray) {
            product.displayInfo();
        }

        Park centralPark = new Park("Центральный парк");
        Park.Attraction wheel = centralPark.new Attraction("Колесо обозрения", "10:00–22:00", 300);
        Park.Attraction rollerCoaster = centralPark.new Attraction("Американские горки", "11:00–21:00", 450);

        centralPark.addAttraction(wheel);
        centralPark.addAttraction(rollerCoaster);

        centralPark.displayAttractions();
    }
}