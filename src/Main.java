import java.time.DayOfWeek;
import java.util.List;

import models.Teacher;
import models.Classroom;
import models.Group;
import models.Subgroup;
import models.LessonNumber;

import lessons.Lesson;
import lessons.Lecture;
import lessons.LabWork;

import manager.Manager;
import exceptions.ScheduleConflictException;

public class Main {

    public static void main(String[] args)
            throws ScheduleConflictException {

        Manager manager = new Manager();

        Teacher teacher = new Teacher("Сирота Е.А.");
        Teacher teacher1 = new Teacher("Соломатин Д.И.");

        Classroom room = new Classroom("385");
        Classroom room1 = new Classroom("386");

        Group group = new Group("11 группа");
        Group group1 = new Group("12 группа");

        Lesson lecture = new Lecture(
                "Матанализ",
                teacher,
                room,
                LessonNumber.FIRST,
                DayOfWeek.MONDAY,
                List.of(group)
        );

        manager.addLessonSafe(lecture);

        System.out.println(
                "Добавлена: " + lecture.getSubjectName()
        );

        Subgroup subgroup = new Subgroup(group, 1);

        Lesson lab = new LabWork(
                "АЭВМ",
                teacher,
                room,
                LessonNumber.SECOND,
                DayOfWeek.MONDAY,
                subgroup
        );

        manager.addLessonSafe(lab);

        System.out.println(
                "Добавлена: " + lab.getSubjectName()
        );

        System.out.println("\n");

        Lesson lecture5 = new Lecture(
                "Программирование",
                teacher,
                room,
                LessonNumber.THIRD,
                DayOfWeek.MONDAY,
                List.of(group1)
        );

        manager.addLessonSafe(lecture5);

        Lesson lecture4 = new Lecture(
                "Матанализ",
                teacher,
                room,
                LessonNumber.FIRST,
                DayOfWeek.TUESDAY,
                List.of(group1)
        );

        manager.addLessonSafe(lecture4);

        Lesson lecture3 = new Lecture(
                "Матанализ",
                teacher,
                room,
                LessonNumber.FIRST,
                DayOfWeek.MONDAY,
                List.of(group1)
        );

        manager.addLessonSafe(lecture3);

        System.out.println(
                "Добавлена: " + lecture.getSubjectName()
        );

        System.out.println("\n");

        Lesson lesson1 = new Lecture(
                "Программирование",
                teacher1,
                room1,
                LessonNumber.FIRST,
                DayOfWeek.MONDAY,
                List.of(group1)
        );

        manager.addLessonSafe(lesson1);

        manager.printScheduleByGroup(group1);

        manager.printScheduleByTeacher(teacher1);

        manager.printScheduleByClassroom(room1);

        System.out.println(
                "Нагрузка преподавателя: "
                        + manager.calculateTeacherHours(teacher)
                        + " ч."
        );
    }
}