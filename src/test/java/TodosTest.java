import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TodosTest {
    SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

    String[] subtasks = { "Молоко", "Яйца", "Хлеб" };
    Epic epic = new Epic(55, subtasks);

    Meeting meeting = new Meeting(
            555,
            "Выкатка 3й версии приложения",
            "Приложение НетоБанка",
            "Во вторник после обеда"
    );

    @Test
    public void testGetItems() {
        Assertions.assertEquals(5, simpleTask.getId());
        Assertions.assertEquals("Позвонить родителям", simpleTask.getTitle());

        Assertions.assertEquals(55, epic.getId());
        Assertions.assertArrayEquals(new String[]{"Молоко", "Яйца", "Хлеб"}, epic.getSubtasks());

        Assertions.assertEquals(555, meeting.getId());
        Assertions.assertEquals("Выкатка 3й версии приложения", meeting.getTopic());
        Assertions.assertEquals("Приложение НетоБанка", meeting.getProject());
        Assertions.assertEquals("Во вторник после обеда", meeting.getStart());
    }

    @Test
    public void shouldAddThreeTasksOfDifferentType() {
        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { simpleTask, epic, meeting };
        Task[] actual = todos.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testMatchesWithSimpleTask() {
        Assertions.assertTrue(simpleTask.matches("Позвонить"));
        Assertions.assertFalse(simpleTask.matches("Написать"));
    }

    @Test
    public void testMatchesWithEpic() {
        Assertions.assertTrue(epic.matches("Хлеб"));
        Assertions.assertFalse(epic.matches("Масло"));
    }

    @Test
    public void testMatchesWithMeeting() {
        Assertions.assertTrue(meeting.matches("Выкатка"));
        Assertions.assertTrue(meeting.matches("НетоБанк"));

        Assertions.assertFalse(meeting.matches("2й версии"));
        Assertions.assertFalse(meeting.matches("НетоБанков"));
    }

    @Test
    public void testSearch() {
        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Assertions.assertArrayEquals(new Task[]{simpleTask}, todos.search("Позвонить"));
        Assertions.assertArrayEquals(new Task[]{epic}, todos.search("Хлеб"));
        Assertions.assertArrayEquals(new Task[]{meeting}, todos.search("Выкатка"));
        Assertions.assertArrayEquals(new Task[]{simpleTask, epic, meeting}, todos.search("о"));

        Assertions.assertArrayEquals(new Task[]{}, todos.search("Написать"));
    }
}
