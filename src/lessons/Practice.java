package lessons;

import java.time.DayOfWeek;

import models.Classroom;
import models.Group;
import models.LessonNumber;
import models.Teacher;

/**
 * Класс практического занятия.
 * Проводится для одной конкретной группы.
 */
public final class Practice extends Lesson {

    private final Group group;

    public Practice(
            String subjectName,
            Teacher teacher,
            Classroom classroom,
            LessonNumber lessonNumber,
            DayOfWeek dayOfWeek,
            Group group
    ) {
        super(
                subjectName,
                teacher,
                classroom,
                lessonNumber,
                dayOfWeek
        );

        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public boolean involvesGroup(Group group) {
        return this.group.equals(group);
    }

    @Override
    public String toString() {
        return String.format(
                "[ПРАКТИКА] %s | %s | %s | %s",
                getSubjectName(),
                getTeacher(),
                getClassroom(),
                group
        );
    }
}