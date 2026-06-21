package task2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneDirectory {
    private final Map<String, List<String>> directory = new HashMap<>();

    public void add(String surname, String phoneNumber) {
        List<String> phones = directory.computeIfAbsent(surname, k -> new ArrayList<>());
        phones.add(phoneNumber);
        System.out.println("Запись добавлена: " + surname + " - " + phoneNumber);
    }

    public void get(String surname) {
        List<String> phones = directory.get(surname);
        if (phones != null && !phones.isEmpty()) {
            System.out.println("Найдено для '" + surname + "':");
            for (String phone : phones) {
                System.out.println(phone);
            }
        } else {
            System.out.println("В справочнике нет записей о фамилии '" + surname + "'.");
        }
    }

    public static void main(String[] args) {
        PhoneDirectory book = new PhoneDirectory();

        book.add("Иванов", "+7-999-111-22-33");
        book.add("Петров", "+7-912-000-11-22");
        book.add("Иванов", "+7-916-555-66-77"); // второй номер Иванова

        System.out.println("Поиск по фамилии Иванов");
        book.get("Иванов");

        System.out.println(" Поиск по фамилии Сидоров");
        book.get("Сидоров");
    }
}