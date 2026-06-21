package task1;

import java.util.*;

public class StudentsProcessor {
    public static void removeBadStudents(Set<Student> students) {

        Set<Student> copy = new HashSet<>(students);
        for (Student s : copy) {
            if (s.getAverageGrade() < 3.0) {
                students.remove(s);
                System.out.println("Студент " + s.getName() + " отчислен за низкий балл.");
            }
        }
    }

    public static void promoteGoodStudents(Set<Student> students) {
        for (Student s : students) {
            if (s.getAverageGrade() >= 3.0) {
                s.setCourse(s.getCourse() + 1);
                System.out.println("Студент " + s.getName() + " переведен на курс " + s.getCourse());
            }
        }
    }

    public static void printStudents(Set<Student> students, int targetCourse) {
        System.out.println("Список студентов " + targetCourse + " курса:");
        boolean found = false;
        for (Student s : students) {
            if (s.getCourse() == targetCourse) {
                System.out.println("- " + s.getName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("(пусто)");
        }
    }

    public static void main(String[] args) {
        Set<Student> students = new HashSet<>();

        Student ivan = new Student("Иван", "ИВТ-101", 1);
        ivan.addGrade("Математика", 4);
        ivan.addGrade("Программирование", 5);
        students.add(ivan);

        Student petr = new Student("Петр", "ИВТ-101", 1);
        petr.addGrade("Математика", 2);
        petr.addGrade("История", 3);
        students.add(petr);

        Student anna = new Student("Анна", "ЭКН-202", 2);
        anna.addGrade("Экономика", 5);
        anna.addGrade("Статистика", 4);
        students.add(anna);

        System.out.println("Начало учебного года");
        printStudents(students, 1);

        System.out.println("Сессия завершена");
        removeBadStudents(students); // отчисление
        promoteGoodStudents(students); // перевод

        System.out.println("Новый учебный год");
        printStudents(students, 2);
    }
}