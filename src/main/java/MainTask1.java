public class MainTask1 {
    public static void main(String[] args) {
        Dog dogBob = new Dog("Боб");
        dogBob.run(150);
        dogBob.swim(15);

        Cat catBars = new Cat("Барс");
        catBars.run(250);
        catBars.swim(5);

        Cat[] catsArray = {new Cat("Игорь"), new Cat("Вася"), catBars};
        int foodInBowl = 25;

        System.out.println("Кормление котов:");
        for (Cat cat : catsArray) {
            int appetite = 10;

            if (foodInBowl >= appetite) {
                cat.eat(appetite);
                foodInBowl -= appetite;
                System.out.println("В миске осталось еды: " + foodInBowl);
            } else {
                System.out.println(cat.getName() + " не поел. В миске мало еды (" + foodInBowl + ").");
            }
            if (cat.getName().equals("Вася")) {
                foodInBowl += 15;
                System.out.println("В миску добавили 15 еды. Теперь еды: " + foodInBowl);
            }

            System.out.println();
        }

        System.out.println("Информация о сытости котов:");
        for (Cat cat : catsArray) {
            System.out.println(cat.getName() + ": " + (cat.isFull() ? "Сыт" : "Голоден"));
        }

        System.out.println("Статистика созданных объектов:");
        System.out.println("Всего животных: " + Animal.getTotalAnimals());
        System.out.println("Собак: " + Dog.getTotalDogs());
        System.out.println("Котов: " + Cat.getTotalCats());
    }
}