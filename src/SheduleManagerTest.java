import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SheduleManagerTest{

    private Manager manager;
    private Models.Teacher teacher1;
    private Models.Teacher teacher2;
    private Models.Classroom room1;
    private Models.Classroom room2;
    private Models.Group group1;
    private Models.Group group2;
    private Models.Subgroup subgroup1_1;

    @BeforeEach
    void setUp() {
        manager = new Manager();
        teacher1 = new Models.Teacher("Щербаков М.В.");
        teacher2 = new Models.Teacher("Соломатин Д.И.");
        room1 = new Models.Classroom("385");
        room2 = new Models.Classroom("297");
        group1 = new Models.Group("11 группа");
        group2 = new Models.Group("12 группа");
        subgroup1_1 = new Models.Subgroup(group1, 1);
    }

    // --- 1. Успешное добавление занятий ---

    @Test
    void testAddLesson_Success() {
        assertDoesNotThrow(() -> {
            manager.addLesson(new Lecture("Матанализ", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, List.of(group1)));
        });
    }

    @Test
    void testAddLesson_DifferentTimes_Success() throws ScheduleConflictException {
        manager.addLesson(new Lecture("Матанализ", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, List.of(group1)));

        // Вторая пара в тот же день с тем же учителем — конфликта нет
        assertDoesNotThrow(() -> {
            manager.addLesson(new Practice("Java", teacher1, room1, Models.LessonNumber.SECOND, DayOfWeek.MONDAY, group1));
        });
    }

    @Test
    void testAddLesson_DifferentDays_Success() throws ScheduleConflictException {
        manager.addLesson(new Lecture("Матанализ", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, List.of(group1)));

        // Вторник, 1-я пара — конфликта нет
        assertDoesNotThrow(() -> {
            manager.addLesson(new Lecture("Матанализ", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.TUESDAY, List.of(group1)));
        });
    }

    // --- 2. Проверка конфликтов ---

    @Test
    void testAddLesson_ClassroomConflict() throws ScheduleConflictException {
        manager.addLesson(new Lecture("Матанализ", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, List.of(group1)));

        // Та же аудитория, то же время, но другой учитель и группа
        ScheduleConflictException exception = assertThrows(ScheduleConflictException.class, () -> {
            manager.addLesson(new Practice("Java", teacher2, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, group2));
        });
        assertTrue(exception.getMessage().contains("Аудитория 385 уже занята"));
    }

    @Test
    void testAddLesson_TeacherConflict() throws ScheduleConflictException {
        manager.addLesson(new Lecture("Матанализ", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, List.of(group1)));

        // Тот же учитель, то же время, но другая аудитория
        ScheduleConflictException exception = assertThrows(ScheduleConflictException.class, () -> {
            manager.addLesson(new Practice("Java", teacher1, room2, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, group2));
        });
        assertTrue(exception.getMessage().contains("Преподаватель Щербаков М.В. уже ведет другое занятие"));
    }

    @Test
    void testAddLesson_GroupConflict_PracticeAndLecture() throws ScheduleConflictException {
        manager.addLesson(new LabWork("АЭВМ", teacher1, room1, Models.LessonNumber.FIRST,
                DayOfWeek.MONDAY, subgroup1_1));

        // Группа 11 уже занята на лекции
        ScheduleConflictException exception = assertThrows(ScheduleConflictException.class, () -> {
            manager.addLesson(new Practice("Java", teacher2, room2, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, group1));
        });
        assertTrue(exception.getMessage().contains("Накладка у студентов"));
    }

    // --- 3. Подсчёт академических часов ---

    @Test
    void testCalculateTeacherHours_NoLessons() {
        assertEquals(0, manager.calculateTeacherHours(teacher1));
    }

    @Test
    void testCalculateTeacherHours_MultipleLessons() throws ScheduleConflictException {
        manager.addLesson(new Lecture("Матанализ", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, List.of(group1)));
        manager.addLesson(new LabWork("АЭВМ", teacher1, room2, Models.LessonNumber.SECOND, DayOfWeek.MONDAY, subgroup1_1));

        // 2 занятия по 2 ак. часа = 4 часа
        assertEquals(4, manager.calculateTeacherHours(teacher1));
    }

    @Test
    void testCalculateTeacherHours_OnlyTargetTeacherCounted() throws ScheduleConflictException {
        manager.addLesson(new Lecture("Матанализ", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, List.of(group1)));
        manager.addLesson(new Practice("Java", teacher2, room2, Models.LessonNumber.SECOND, DayOfWeek.MONDAY, group1));

        assertEquals(2, manager.calculateTeacherHours(teacher1));
        assertEquals(2, manager.calculateTeacherHours(teacher2));
    }

    // --- 4. Проверка работы метода involvesGroup ---

    @Test
    void testInvolvesGroup_Lecture() {
        Lecture lecture = new Lecture("Матанализ", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, List.of(group1));
        assertTrue(lecture.involvesGroup(group1));
        assertFalse(lecture.involvesGroup(group2));
    }

    @Test
    void testInvolvesGroup_Practice() {
        Practice practice = new Practice("Java", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, group1);
        assertTrue(practice.involvesGroup(group1));
        assertFalse(practice.involvesGroup(group2));
    }

    @Test
    void testInvolvesGroup_LabWork() {
        LabWork lab = new LabWork("АЭВМ", teacher1, room1, Models.LessonNumber.FIRST, DayOfWeek.MONDAY, subgroup1_1);
        assertTrue(lab.involvesGroup(group1));
        assertFalse(lab.involvesGroup(group2));
    }

    // --- 5. Проверка эквивалентности сущностей (equals/hashCode) ---

    @Test
    void testTeacherEquals() {
        Models.Teacher t1 = new Models.Teacher("Щербаков М.В.");
        Models.Teacher t2 = new Models.Teacher("Щербаков М.В.");
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testGroupEquals() {
        Models.Group g1 = new Models.Group("11 группа");
        Models.Group g2 = new Models.Group("11 группа");
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }
}