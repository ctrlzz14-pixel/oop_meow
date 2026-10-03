package models;

import java.util.Objects;

/**
 * Класс для представления преподавателя.
 */
public class Teacher {

    private final String fullName;

    /**
     * @param fullName ФИО преподавателя
     */
    public Teacher(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(fullName, teacher.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

    @Override
    public String toString() {
        return fullName;
    }
}