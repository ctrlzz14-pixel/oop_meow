package lessons;

import java.time.DayOfWeek;

import models.Classroom;
import models.Group;
import models.LessonNumber;
import models.Teacher;

/**
 * Базовый абстрактный класс для всех типов учебных занятий.
 *
 * @author Вялых Д. С.
 */
public sealed abstract class Lesson
        permits Lecture, Practice, LabWork {

    private final String subjectName;
    private final Teacher teacher;
    private final Classroom classroom;
    private final LessonNumber lessonNumber;
    private final DayOfWeek dayOfWeek;

    /**
     * Инициализация общих полей для любого занятия.
     */
    public Lesson(
            String subjectName,
            Teacher teacher,
            Classroom classroom,
            LessonNumber lessonNumber,
            DayOfWeek dayOfWeek
    ) {
        this.subjectName = subjectName;
        this.teacher = teacher;
        this.classroom = classroom;
        this.lessonNumber = lessonNumber;
        this.dayOfWeek = dayOfWeek;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public LessonNumber getLessonNumber() {
        return lessonNumber;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Проверяет, касается ли данное занятие указанной группы.
     *
     * @param group проверяемая группа
     * @return true, если группа посещает это занятие
     */
    public abstract boolean involvesGroup(Group group);

    /**
     * Возвращает длительность занятия в академических часах.
     */
    public int getAcademicHours() {
        return 2;
    }
}