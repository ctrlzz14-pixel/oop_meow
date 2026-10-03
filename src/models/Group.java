package models;

import java.util.Objects;

/**
 * Студенческая учебная группа.
 */
public class Group {

    private final String name;

    /**
     * @param name Название или номер группы
     */
    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Группа " + name;
    }
}