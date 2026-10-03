package lessons;

import java.time.DayOfWeek;

import models.Classroom;
import models.Group;
import models.LessonNumber;
import models.Subgroup;
import models.Teacher;

/**
 * Класс лабораторной работы.
 * Проводится для одной подгруппы.
 */
public final class LabWork extends Lesson {

    private final Subgroup subgroup;

    public LabWork(
            String subjectName,
            Teacher teacher,
            Classroom classroom,
            LessonNumber lessonNumber,
            DayOfWeek dayOfWeek,
            Subgroup subgroup
    ) {
        super(
                subjectName,
                teacher,
                classroom,
                lessonNumber,
                dayOfWeek
        );

        this.subgroup = subgroup;
    }

    public Subgroup getSubgroup() {
        return subgroup;
    }

    @Override
    public boolean involvesGroup(Group group) {
        return subgroup.getGroup().equals(group);
    }

    @Override
    public String toString() {
        return String.format(
                "[ЛАБОРАТОРНАЯ] %s | %s | %s | %s",
                getSubjectName(),
                getTeacher(),
                getClassroom(),
                subgroup
        );
    }
}