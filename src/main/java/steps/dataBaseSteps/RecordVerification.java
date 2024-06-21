package steps.dataBaseSteps;

import entity.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordVerification {

    public static void updatedShouldConformTemplateInDb(List<Book> actualList) {
        List<Book> filteredList = actualList
                .stream()
                .filter(book -> book.getUpdated().toString().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d+"))
                .toList();

        assertEquals(actualList, filteredList);
    }

    public static void shouldBeEqualsInDb(List<Book> expected, List<Book> actual) {

        assertEquals(expected, actual);
    }
}
