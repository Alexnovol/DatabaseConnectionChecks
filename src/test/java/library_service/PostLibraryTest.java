package library_service;

import entity.Author;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import models.post.SavingNewBookRq;
import models.post.SavingNewBookRs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.requestSteps.RequestSender;

import static steps.asserts.PostLibraryEndpoint.*;
import static steps.requestSteps.RequestSender.postBookResponse;
import static utils.DataHelper.*;

@Epic("Post")
@Story("Сохранение информации")
public class PostLibraryTest {

    @Test
    @DisplayName("Сохранение новой книги. Позитивный кейс")
    @Description("Получен успешный ответ")
    public void postBookSuccess() {
        Author author = getRegisteredAuthor();
        String bookTitle = getBookTitle();

        SavingNewBookRs expectedModel = new SavingNewBookRs();
        expectedModel.setBookId(getIdRegisteredBook(author, bookTitle) + 1);

        SavingNewBookRs actualModel = postBookResponse(new SavingNewBookRq(bookTitle, author))
                .as(SavingNewBookRs.class);

        shouldBeEquals(actualModel, expectedModel);
    }

    @Test
    @DisplayName("Сохранение новой книги без названия с зарегистрированным автором. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBookWithoutTitleWithRegisteredAuthor() {
        Author author = getRegisteredAuthor();

        SavingNewBookRq request = new SavingNewBookRq(null, author);

        Response response = postBookResponse(request);

        checkStatusCode(response, 400);

        commonErrorMessageShouldBeEquals(response, 1001, null);

    }

    @Test
    @DisplayName("Сохранение новой книги без названия с незарегистрированным автором. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBookWithoutTitleWithUnregisteredAuthor() {
        Author author = getUnregisteredAuthor();

        SavingNewBookRq request = new SavingNewBookRq(null, author);

        Response response = RequestSender.postBookResponse(request);

        checkStatusCode(response, 400);

        commonErrorMessageShouldBeEquals(response, 1001, null);
    }

    @Test
    @DisplayName("Сохранение новой книги без автора. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBookWithoutAuthor() {
        SavingNewBookRq request = new SavingNewBookRq(getBookTitle(), null);

        Response response = RequestSender.postBookResponse(request);

        checkStatusCode(response, 400);

        commonErrorMessageShouldBeEquals(response, 1001, null);
    }

    @Test
    @DisplayName("Сохранение новой книги без названия и без автора. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBookWithEmptyRequest() {
        SavingNewBookRq request = new SavingNewBookRq(null, null);

        Response response = RequestSender.postBookResponse(request);

        checkStatusCode(response, 400);

        commonErrorMessageShouldBeEquals(response, 1001, null);
    }

    @Test
    @DisplayName("Сохранение новой книги с незарегистрированным автором. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 409")
    public void postBookWithUnregisteredAuthor() {
        SavingNewBookRq request = new SavingNewBookRq(getBookTitle(), getUnregisteredAuthor());

        Response response = RequestSender.postBookResponse(request);

        checkStatusCode(response, 409);

        commonErrorMessageShouldBeEquals(response, 1004, "Указанный автор не существует в таблице");
    }

    @Test
    @DisplayName("Сохранение двух книг с одним и тем же названием и автором. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBooksWithSameData() {
        Author author = getRegisteredAuthor();
        String bookTitle = getBookTitle();

        SavingNewBookRq request = new SavingNewBookRq(bookTitle, author);

        RequestSender.postBookResponse(request);
        Response response2 = RequestSender.postBookResponse(request);

        checkStatusCode(response2, 400);

        commonErrorMessageShouldBeEquals(response2, 1001, null);
    }

    @Test
    @DisplayName("Сохранение новой книги с автором без Id. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код = 400")
    public void postBookWithAuthorWithoutId() {
        Author author = new Author();

        SavingNewBookRq request = new SavingNewBookRq(getBookTitle(), author);

        Response response = RequestSender.postBookResponse(request);

        checkStatusCode(response, 400);

        commonErrorMessageShouldBeEquals(response, 1001, null);
    }
}
