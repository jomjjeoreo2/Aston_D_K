package task1;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
    private String name;
    private String group;
    private int course;
    private final Map<String, Integer> grades = new HashMap<>();

    public Student(String name, String group, int course) {
        this.name = name;
        this.group = group;
        this.course = course;
    }

    public void addGrade(String subject, int grade) {
        if (grade >= 0 && grade <= 5) {
            grades.put(subject, grade);
        } else {
            System.out.println("Ошибка: Оценка должна быть от 0 до 5");
        }
    }

    public double getAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        int sum = grades.values().stream().mapToInt(Integer::intValue).sum();
        return (double) sum / grades.size();
    }


    public String getName() { return name; }
    public String getGroup() { return group; }
    public int getCourse() { return course; }

    public void setCourse(int course) { this.course = course; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;

        return Objects.equals(name, student.name) && Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, group);
    }
}
