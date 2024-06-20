package library_service;

import entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.dataBaseSteps.RequestExecutor;
import utils.DataHelper;

import java.util.List;

import static utils.DataHelper.getBookTitle;

public class DatabaseLibraryTest {

    private RequestExecutor requestExecutor = new RequestExecutor();


    @Test
    @DisplayName("Проверка базы данных")
    public void databaseTesting() {
        String bookTitle1 = getBookTitle();
        String bookTitle2 = getBookTitle();

        requestExecutor.deleteAll();

        requestExecutor.insertBook(bookTitle1, (long) 1);
        requestExecutor.insertBook(bookTitle2, (long) 2);

        List<Book> books = requestExecutor.findAll();
        System.out.println(books);

        List<Book> oneBook = requestExecutor.findByBookTitle(bookTitle1);
        System.out.println(oneBook);

        requestExecutor.deleteByBookTitle(bookTitle2);

        List<Book> remainingBook = requestExecutor.findAll();
        System.out.println(remainingBook);

    }
}
