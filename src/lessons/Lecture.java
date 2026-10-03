package lessons;

import java.time.DayOfWeek;
import java.util.List;

import models.Classroom;
import models.Group;
import models.LessonNumber;
import models.Teacher;

/**
 * Класс лекции. Лекцию могут посещать сразу несколько групп.
 */
public final class Lecture extends Lesson {

    private final List<Group> groups;

    public Lecture(
            String subjectName,
            Teacher teacher,
            Classroom classroom,
            LessonNumber lessonNumber,
            DayOfWeek dayOfWeek,
            List<Group> groups
    ) {
        super(
                subjectName,
                teacher,
                classroom,
                lessonNumber,
                dayOfWeek
        );

        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public boolean involvesGroup(Group group) {
        return groups.contains(group);
    }

    @Override
    public String toString() {
        return String.format(
                "[ЛЕКЦИЯ] %s | %s | %s | Группы: %s",
                getSubjectName(),
                getTeacher(),
                getClassroom(),
                groups
        );
    }
}
