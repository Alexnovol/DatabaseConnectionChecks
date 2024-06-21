package library_service;

import entity.Author;
import entity.Book;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import models.post.SavingNewBookRq;
import models.post.SavingNewBookRs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.dataBaseSteps.RequestExecutor;
import steps.requestSteps.RequestSender;

import java.util.ArrayList;
import java.util.List;

import static steps.asserts.PostLibraryEndpoint.*;
import static steps.dataBaseSteps.RecordVerification.*;
import static steps.requestSteps.RequestSender.postBookResponse;
import static utils.DataHelper.*;

@Epic("Post")
@Story("Сохранение информации")
public class PostLibraryTest {

    private RequestExecutor requestExecutor = new RequestExecutor();

    @BeforeEach
    public void cleanDatabase() {
        requestExecutor.deleteAll();
    }

    @Test
    @DisplayName("Сохранение новой книги. Позитивный кейс")
    @Description("Получен успешный ответ")
    public void postBookSuccess() {
        Author author = getRegisteredAuthor();
        String bookTitle = getBookTitle();
        long expBookId = getIdRegisteredBook(author, getBookTitle(), getCurrentDateTime()) + 1;

        SavingNewBookRs expectedModel = new SavingNewBookRs();
        expectedModel.setBookId(expBookId);

        SavingNewBookRs actualModel = postBookResponse(new SavingNewBookRq(bookTitle, author))
                .as(SavingNewBookRs.class);

        shouldBeEquals(actualModel, expectedModel);

        List<Book> actualList = requestExecutor.findByBookTitle(bookTitle);

        updatedShouldConformTemplateInDb(actualList);

        actualList.forEach(book -> book.setUpdated(null));

        shouldBeEqualsInDb(getExpBookList(expBookId, bookTitle, author.getId(), null), actualList);

    }

    @Test
    @DisplayName("Сохранение новой книги без названия с зарегистрированным автором. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBookWithoutTitleWithRegisteredAuthor() {
        Author author = getRegisteredAuthor();

        SavingNewBookRq request = new SavingNewBookRq(null, author);

        Response response = postBookResponse(request);

        checkStatusCode(response, 400);

        commonErrorMessageShouldBeEquals(response, "1001", null);

        shouldBeEqualsInDb(getEmptyList(), requestExecutor.findAll());
    }

    @Test
    @DisplayName("Сохранение новой книги без названия с незарегистрированным автором. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBookWithoutTitleWithUnregisteredAuthor() {
        Author author = getUnregisteredAuthor();

        SavingNewBookRq request = new SavingNewBookRq(null, author);

        Response response = RequestSender.postBookResponse(request);

        checkStatusCode(response, 400);

        commonErrorMessageShouldBeEquals(response, "1001", null);

        shouldBeEqualsInDb(getEmptyList(), requestExecutor.findAll());
    }

    @Test
    @DisplayName("Сохранение новой книги без автора. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBookWithoutAuthor() {
        SavingNewBookRq request = new SavingNewBookRq(getBookTitle(), null);

        Response response = RequestSender.postBookResponse(request);

        checkStatusCode(response, 400);

        commonErrorMessageShouldBeEquals(response, "1001", null);

        shouldBeEqualsInDb(getEmptyList(), requestExecutor.findAll());
    }

    @Test
    @DisplayName("Сохранение новой книги без названия и без автора. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBookWithEmptyRequest() {
        SavingNewBookRq request = new SavingNewBookRq(null, null);

        Response response = RequestSender.postBookResponse(request);

        checkStatusCode(response, 400);

        commonErrorMessageShouldBeEquals(response, "1001", null);

        shouldBeEqualsInDb(getEmptyList(), requestExecutor.findAll());
    }

    @Test
    @DisplayName("Сохранение новой книги с незарегистрированным автором. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 409")
    public void postBookWithUnregisteredAuthor() {
        SavingNewBookRq request = new SavingNewBookRq(getBookTitle(), getUnregisteredAuthor());

        Response response = RequestSender.postBookResponse(request);

        checkStatusCode(response, 409);

        commonErrorMessageShouldBeEquals(response, "1004", "Указанный автор не существует в таблице");

        shouldBeEqualsInDb(getEmptyList(), requestExecutor.findAll());
    }

    @Test
    @DisplayName("Сохранение двух книг с одним и тем же названием и автором. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBooksWithSameData() {
        Author author = getRegisteredAuthor();
        String bookTitle = getBookTitle();

        SavingNewBookRq request = new SavingNewBookRq(bookTitle, author);

        RequestSender.postBookResponse(request);
        Response response2 = postBookResponse(request);

        checkStatusCode(response2, 400);

        commonErrorMessageShouldBeEquals(response2, "1001", null);

        List<Book> expectedList = new ArrayList<>();
        expectedList.add(
                new Book(getIdRegisteredBook(author, getBookTitle(), getCurrentDateTime()) - 2, bookTitle, author.getId(), null));

        List<Book> actualList = requestExecutor.findByBookTitle(bookTitle);
        actualList.forEach(book -> book.setUpdated(null));

        shouldBeEqualsInDb(expectedList, actualList);
    }

    @Test
    @DisplayName("Сохранение новой книги с автором без Id. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBookWithAuthorWithoutId() {
        Author author = new Author();

        SavingNewBookRq request = new SavingNewBookRq(getBookTitle(), author);

        Response response = RequestSender.postBookResponse(request);

        checkStatusCode(response, 400);

        commonErrorMessageShouldBeEquals(response, "1001", null);

        shouldBeEqualsInDb(getEmptyList(), requestExecutor.findAll());
    }
}
