package models;
import java.util.Objects;

/**
 * Подгруппа конкретной учебной группы (используется для лабораторных).
 */
public class Subgroup {

    private final Group group;
    private final int subgroupNumber;

    /**
     * @param group Основная группа
     * @param subgroupNumber Номер подгруппы (1 или 2)
     */
    public Subgroup(Group group, int subgroupNumber) {
        this.group = group;
        this.subgroupNumber = subgroupNumber;
    }

    public Group getGroup() {
        return group;
    }

    public int getSubgroupNumber() {
        return subgroupNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subgroup subgroup = (Subgroup) o;
        return subgroupNumber == subgroup.subgroupNumber
                && Objects.equals(group, subgroup.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, subgroupNumber);
    }

    @Override
    public String toString() {
        return group.getName() + " (подгруппа " + subgroupNumber + ")";
    }
}