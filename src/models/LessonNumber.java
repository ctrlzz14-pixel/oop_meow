package models;

import java.time.LocalTime;

/**
 * Перечисление пар с привязанным временем начала и окончания.
 */
public enum LessonNumber {

    FIRST(1, LocalTime.of(8, 0), LocalTime.of(9, 35)),
    SECOND(2, LocalTime.of(9, 45), LocalTime.of(11, 20)),
    THIRD(3, LocalTime.of(11, 30), LocalTime.of(13, 5)),
    FOURTH(4, LocalTime.of(13, 25), LocalTime.of(15, 0)),
    FIFTH(5, LocalTime.of(15, 10), LocalTime.of(16, 45)),
    SIXTH(6, LocalTime.of(16, 55), LocalTime.of(18, 30)),
    SEVENTH(7, LocalTime.of(18, 40), LocalTime.of(20, 0)),
    EIGHTH(8, LocalTime.of(20, 10), LocalTime.of(21, 30));

    private final int number;
    private final LocalTime startTime;
    private final LocalTime endTime;

    LessonNumber(int number, LocalTime startTime, LocalTime endTime) {
        this.number = number;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getNumber() {
        return number;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return number + "-я пара (" + startTime + " - " + endTime + ")";
    }
}
