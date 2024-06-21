package utils;

import entity.Author;
import entity.Book;
import steps.dataBaseSteps.RequestExecutor;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class DataHelper {

    private static RequestExecutor requestExecutor = new RequestExecutor();

    public static Author getRegisteredAuthor() {
        String firstName = randomAlphabetic(10, 50);
        String familyName = randomAlphabetic(10, 50);
        String secondName = randomAlphabetic(10, 50);
        String strBirthDate = "1985-01-01";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = null;
        try {
            birthDate = formatter.parse(strBirthDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        requestExecutor.insertAuthor(firstName, familyName, secondName, birthDate);

        return requestExecutor.findAuthor(firstName, familyName, secondName, birthDate);
    }

    public static String getBookTitle() {

        return randomAlphabetic(10, 50);
    }

    public static long getIdRegisteredBook(Author author, String bookTitle, Timestamp updated) {

        requestExecutor.insertBook(bookTitle, author.getId(), updated);

        List<Book> books = requestExecutor.findByBookTitle(bookTitle);

        return books.get(0).getId();

    }

    public static Author getUnregisteredAuthor() {

        Author author = new Author();
        author.setId(getRegisteredAuthor().getId() + 100);

        return author;
    }

    public static List<Book> getEmptyList() {

        return new ArrayList<>();
    }

    public static Timestamp getCurrentDateTime() {

        Date update = new Date();

        return new Timestamp(update.getTime());
    }

    public static String getDateTimeUtc(Timestamp updated) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return simpleDateFormat.format(updated) + "+00:00";
    }

    public static List<Book> getExpBookList(long bookId, String bookTitle, long authorId, Timestamp updated) {

        List<Book> expectedList = new ArrayList<>();
        expectedList.add(new Book(bookId, bookTitle, authorId, updated));

        return expectedList;

    }
}
