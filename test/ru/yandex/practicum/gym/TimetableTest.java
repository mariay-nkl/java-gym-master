package ru.yandex.practicum.gym;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;


public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size());

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondayAt13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertTrue(mondayAt13.size());

        List<TrainingSession> mondayAt14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertTrue(mondayAt14.isEmpty());
    }   

    @Test
    void testMultipleSessionsSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");

        Group group1 = new Group("Йога", Age.ADULT, 60);
        Group group2 = new Group("Аэробика", Age.ADULT, 60);
        TrainingSession session1 = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        assertEquals(2, sessions.size());
    }

    @Test
    void testTrainingSessionsAreSortedByTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Смирнов", "Андрей", "Владимирович");
        Group group = new Group("Фитнес", Age.ADULT, 60);

        TrainingSession session1 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(15, 30));
        TrainingSession session2 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(9, 0));
        TrainingSession session3 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(12, 15));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(3, mondaySessions.size());
        assertEquals(session2, mondaySessions.get(0));
        assertEquals(session3, mondaySessions.get(1));
        assertEquals(session1, mondaySessions.get(2));
    }

    @Test
    void testDifferentDaysOfWeek() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Кузнецов", "Дмитрий", "Игоревич");
        Group group = new Group("Аэробика", Age.ADULT, 45);

        TrainingSession mondaySession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession wednesdaySession = new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));
        TrainingSession fridaySession = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondaySession);
        timetable.addNewTrainingSession(wednesdaySession);
        timetable.addNewTrainingSession(fridaySession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY).size());
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY).size());
        assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
        assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).isEmpty());
    }

    @Test
    void testGetCountByCoachesEmpty() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> coachCounts = timetable.getCountByCoaches();
        assertTrue(coachCounts.isEmpty());
    }

    @Test
    void testGetCountByCoachesMultipleCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");

        Group group = new Group("Общая группа", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        List<CounterOfTrainings> coachCounts = timetable.getCountByCoaches();

        assertEquals(2, coachCounts.size());
        assertEquals(coach1, coachCounts.get(0).getCoach());
        assertEquals(2, coachCounts.get(0).getCount());
        assertEquals(coach2, coachCounts.get(1).getCoach());
        assertEquals(1, coachCounts.get(1).getCount());
    }

    @Test
    void testGetCountByCoachesSingleCoachMultipleTrainings() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Сидоров", "Алексей", "Николаевич");
        Group group = new Group("Детская гимнастика", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(12, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        assertEquals(1, result.size());
        assertEquals(coach, result.get(0).getCoach());
        assertEquals(4, result.get(0).getCount());
    }
}



