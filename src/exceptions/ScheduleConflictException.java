package exceptions;

/**
 * Пользовательское исключение для обработки конфликтов в расписании.
 */
public class ScheduleConflictException extends Exception {

    public ScheduleConflictException(String message) {
        super(message);
    }
}
