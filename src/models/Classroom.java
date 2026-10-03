package models;

import java.util.Objects;

/**
 * Класс для аудитории.
 */
public class Classroom {

    private final String number;

    /**
     * @param number Номер или название кабинета
     */
    public Classroom(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return Objects.equals(number, classroom.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "ауд. " + number;
    }
}
