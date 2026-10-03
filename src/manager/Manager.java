package manager;

import exceptions.ScheduleConflictException;
import lessons.LabWork;
import lessons.Lecture;
import lessons.Lesson;
import lessons.Practice;
import models.Classroom;
import models.Group;
import models.Teacher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Главный менеджер для управления расписанием.
 * Сохраняет список пар, проверяет накладки по аудиториям,
 * преподавателям и группам.
 *
 * @author Вялых Д. С.
 */
public class Manager {

    private final List<Lesson> lessons = new ArrayList<>();

    /**
     * Добавляет занятие в список.
     * Проверяет, не заняты ли на этот день и пару аудитория,
     * преподаватель или студенты.
     *
     * @param newLesson добавляемая пара
     * @throws ScheduleConflictException если найден конфликт
     *                                   со старыми занятиями
     */
    public void addLesson(Lesson newLesson)
            throws ScheduleConflictException {

        for (Lesson existing : lessons) {

            if (existing.getDayOfWeek() == newLesson.getDayOfWeek()
                    && existing.getLessonNumber()
                    == newLesson.getLessonNumber()) {

                if (existing.getClassroom()
                        .equals(newLesson.getClassroom())) {

                    throw new ScheduleConflictException(
                            String.format(
                                    "Аудитория %s уже занята! " +
                                            "Накладка занятий '%s' и '%s' (%s, %s)",
                                    newLesson.getClassroom().getNumber(),
                                    existing.getSubjectName(),
                                    newLesson.getSubjectName(),
                                    newLesson.getDayOfWeek(),
                                    newLesson.getLessonNumber()
                            )
                    );
                }

                if (existing.getTeacher()
                        .equals(newLesson.getTeacher())) {

                    throw new ScheduleConflictException(
                            String.format(
                                    "Преподаватель %s уже ведет " +
                                            "другое занятие! (%s, %s)",
                                    newLesson.getTeacher().getFullName(),
                                    newLesson.getDayOfWeek(),
                                    newLesson.getLessonNumber()
                            )
                    );
                }

                if (hasGroupIntersection(existing, newLesson)) {

                    throw new ScheduleConflictException(
                            String.format(
                                    "Накладка у студентов! " +
                                            "Группа уже занята (%s, %s)",
                                    newLesson.getDayOfWeek(),
                                    newLesson.getLessonNumber()
                            )
                    );
                }
            }
        }

        lessons.add(newLesson);
    }

    /**
     * Подсчитывает суммарное число академических часов преподавателя.
     */
    public int calculateTeacherHours(Teacher teacher) {

        int totalHours = 0;

        for (Lesson lesson : lessons) {
            if (lesson.getTeacher().equals(teacher)) {
                totalHours += lesson.getAcademicHours();
            }
        }

        return totalHours;
    }

    /**
     * Выводит расписание для выбранной группы.
     */
    public void printScheduleByGroup(Group group) {

        System.out.println(
                "Расписание для группы: " + group.getName()
        );

        List<Lesson> sortedLessons = lessons.stream()
                .filter(lesson -> lesson.involvesGroup(group))
                .sorted(
                        Comparator
                                .comparing(Lesson::getDayOfWeek)
                                .thenComparing(Lesson::getLessonNumber)
                )
                .toList();

        for (Lesson lesson : sortedLessons) {
            System.out.println(
                    lesson.getDayOfWeek() + " | "
                            + lesson.getLessonNumber() + " | "
                            + lesson
            );
        }

        System.out.println();
    }

    /**
     * Выводит расписание конкретного преподавателя.
     */
    public void printScheduleByTeacher(Teacher teacher) {

        System.out.println(
                "Расписание преподавателя: "
                        + teacher.getFullName()
        );

        List<Lesson> sortedLessons = lessons.stream()
                .filter(
                        lesson -> lesson.getTeacher().equals(teacher)
                )
                .sorted(
                        Comparator
                                .comparing(Lesson::getDayOfWeek)
                                .thenComparing(Lesson::getLessonNumber)
                )
                .toList();

        for (Lesson lesson : sortedLessons) {
            System.out.println(
                    lesson.getDayOfWeek() + " | "
                            + lesson.getLessonNumber() + " | "
                            + lesson
            );
        }

        System.out.println();
    }

    /**
     * Выводит расписание занятий в указанной аудитории.
     */
    public void printScheduleByClassroom(Classroom classroom) {

        System.out.println(
                "Расписание аудитории: "
                        + classroom.getNumber()
        );

        List<Lesson> sortedLessons = lessons.stream()
                .filter(
                        lesson -> lesson.getClassroom().equals(classroom)
                )
                .sorted(
                        Comparator
                                .comparing(Lesson::getDayOfWeek)
                                .thenComparing(Lesson::getLessonNumber)
                )
                .toList();

        for (Lesson lesson : sortedLessons) {
            System.out.println(
                    lesson.getDayOfWeek() + " | "
                            + lesson.getLessonNumber() + " | "
                            + lesson
            );
        }

        System.out.println();
    }

    /**
     * Проверяет, пересекаются ли группы у двух разных пар.
     */
    private boolean hasGroupIntersection(
            Lesson l1,
            Lesson l2
    ) {

        List<Group> groups1 = getGroupsFromLesson(l1);
        List<Group> groups2 = getGroupsFromLesson(l2);

        for (Group g1 : groups1) {
            if (groups2.contains(g1)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Достает список всех групп,
     * задействованных в занятии.
     */
    private List<Group> getGroupsFromLesson(Lesson lesson) {

        if (lesson instanceof Lecture lecture) {
            return lecture.getGroups();

        } else if (lesson instanceof Practice practice) {
            return List.of(practice.getGroup());

        } else if (lesson instanceof LabWork labWork) {
            return List.of(
                    labWork.getSubgroup().getGroup()
            );
        }

        return List.of();
    }

    /**
     * Безопасное добавление занятия с автоматической
     * обработкой ошибок.
     *
     * Если возникает конфликт, выводит сообщение в консоль
     * и продолжает работу.
     */
    public void addLessonSafe(Lesson newLesson) {

        try {
            addLesson(newLesson);

            System.out.println(
                    "Успешно добавлено: "
                            + newLesson.getSubjectName()
                            + " ("
                            + newLesson.getDayOfWeek()
                            + ", "
                            + newLesson.getLessonNumber().getNumber()
                            + "-я пара)"
            );

        } catch (ScheduleConflictException e) {

            System.out.println(
                    "[ОШИБКА] Не удалось добавить занятие: "
                            + e.getMessage()
            );
        }
    }
}