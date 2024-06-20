package library_service;

import entity.Author;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import models.get.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.requestSteps.RequestSender;
import java.util.*;


import static steps.asserts.GetLibraryEndpoint.*;
import static steps.asserts.GetLibraryEndpoint.checkStatusCodeGetBooksJson;
import static steps.requestSteps.RequestSender.getBooksJsonResponse;
import static steps.requestSteps.RequestSender.getBooksXmlResponse;
import static utils.DataHelper.*;

@Epic("Get")
@Story("Получение информации")
public class GetLibraryTest {

    @Test
    @DisplayName("Получение списка книг в формате Json. Позитивный кейс")
    @Description("Получены ранее сохраненные книги")
    public void getBooksJsonSuccess() {
        Author author = getRegisteredAuthor();
        String bookTitle = getBookTitle();
        long bookId = getIdRegisteredBook(author, bookTitle);

        GettingAuthorsBooksRq request = new GettingAuthorsBooksRq(Long.toString(author.getId()));

        GettingAuthorsBooksRs expectedModel = new GettingAuthorsBooksRs();
        expectedModel.setBook(new GettingAuthorsBooksRs.Book(bookId, bookTitle, author));
        List<GettingAuthorsBooksRs> expectedList = new ArrayList<>();
        expectedList.add(expectedModel);

        List<GettingAuthorsBooksRs> actualList = getBooksJsonResponse(request);
        List<String> updatedList = new ArrayList<>();
        actualList.forEach(model -> updatedList.add(model.getBook().getUpdated()));

        shouldConformTemplate(updatedList);

        actualList.forEach(model -> model.getBook().setUpdated(null));

        shouldBeEquals(actualList, expectedList);

    }

    @Test
    @DisplayName("Получение списка книг в формате Xml. Позитивный кейс")
    @Description("Получены ранее сохраненные книги")
    public void getBooksXmlSuccess() {
        Author author = getRegisteredAuthor();
        String bookTitle = getBookTitle();
        long bookId = getIdRegisteredBook(author, bookTitle);

        List<GettingAuthorsBooksXmlRs.Book> expectedList = new ArrayList<>();
        expectedList.add(new GettingAuthorsBooksXmlRs.Book(bookId, bookTitle, author));
        GettingAuthorsBooksXmlRs expectedModel = new GettingAuthorsBooksXmlRs();
        expectedModel.setBooks(expectedList);

        GettingAuthorsBooksXmlRs actualModel = getBooksXmlResponse(new GettingAuthorsBooksXmlRq(author))
                .as(GettingAuthorsBooksXmlRs.class);

        List<String> updatedList = new ArrayList<>();

        actualModel.getBooks().forEach(book -> updatedList.add(book.getUpdated()));

        shouldConformTemplate(updatedList);

        actualModel.getBooks().forEach(book -> book.setUpdated(null));

        shouldBeEquals(actualModel, expectedModel);

    }

    @Test
    @DisplayName("Получение книг автора в формате Xml, у которого отсутствуют книги. Позитивный кейс")
    @Description("Сервис вернул Http код 200. Получен пустой список")
    public void getBooksXmlAuthorWithoutBooks() {
        Author author = getRegisteredAuthor();

        GettingAuthorsBooksXmlRq request = new GettingAuthorsBooksXmlRq(author);

        Response response = getBooksXmlResponse(request);

        GettingAuthorsBooksXmlRs expectedModel = new GettingAuthorsBooksXmlRs();
        expectedModel.setBooks(new ArrayList<>());

        GettingAuthorsBooksXmlRs actualModel = response
                .as(GettingAuthorsBooksXmlRs.class);

        checkStatusCode(response, 200);

        shouldBeEquals(actualModel, expectedModel);
    }

    @Test
    @DisplayName("Получение книг автора в формате Json, у которого отсутствуют книги. Позитивный кейс")
    @Description("Сервис вернул Http код 200. Получен пустой список")
    public void getBooksJsonAuthorWithoutBooks() {
        Author author = getRegisteredAuthor();

        GettingAuthorsBooksRq request = new GettingAuthorsBooksRq(Long.toString(author.getId()));

        List<GettingAuthorsBooksRs> actualList = RequestSender.getBooksJsonResponse(request);

        List<GettingAuthorsBooksRs> expectedList = new ArrayList<>();

        shouldBeEquals(actualList, expectedList);

    }

    @Test
    @DisplayName("Получение книг в формате Xml при передаче пустого Id. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код 400")
    public void getBooksXmlWithEmptyId() {
        Author author = new Author();

        GettingAuthorsBooksXmlRq request = new GettingAuthorsBooksXmlRq(author);

        Response response = RequestSender.getBooksXmlResponse(request);

        checkStatusCode(response, 400);

        commonErrorMessageShouldBeEquals(response, 1001, "Не передан id автора");

    }

    @Test
    @DisplayName("Получение книг в формате Xml у незарегистрированного автора. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код 400")
    public void getBooksXmlWithUnregisteredAuthor() {
        Author author = getUnregisteredAuthor();

        GettingAuthorsBooksXmlRq request = new GettingAuthorsBooksXmlRq(author);

        Response response = getBooksXmlResponse(request);

        checkStatusCode(response, 409);

        commonErrorMessageShouldBeEquals(response, 1004, "Указанный автор не существует в таблице");
    }

    @Test
    @DisplayName("Получение книг в формате Json у незарегистрированного автора. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код 400")
    public void getBooksJsonWithUnregisteredAuthor() {
        Author author = getUnregisteredAuthor();

        GettingAuthorsBooksRq request = new GettingAuthorsBooksRq(Long.toString(author.getId()));

        List<GettingAuthorsBooksRs> books = RequestSender.getBooksJsonResponse(request);

        GettingAuthorsBooksRs actualModel = books.get(0);

        checkStatusCodeGetBooksJson(actualModel, 409);

        GettingAuthorsBooksRs expectedModel = new GettingAuthorsBooksRs();
        expectedModel.setBook(new GettingAuthorsBooksRs.Book(0, null, null));
        expectedModel.setStatusCode(409);
        expectedModel.setErrorCode(1004);
        expectedModel.setErrorMessage("Указанный автор не существует в таблице");

        shouldBeEquals(actualModel, expectedModel);

    }

    @Test
    @DisplayName("Получение книг в формате Json при передаче пустого Id. Негативный кейс")
    @Description("Сервис вернул ошибку и Http код 400")
    public void getBooksJsonWithEmptyId() {
        GettingAuthorsBooksRq request = new GettingAuthorsBooksRq();

        List<GettingAuthorsBooksRs> books = RequestSender.getBooksJsonResponse(request);

        GettingAuthorsBooksRs actualModel = books.get(0);

        checkStatusCodeGetBooksJson(actualModel, 400);

        GettingAuthorsBooksRs expectedModel = new GettingAuthorsBooksRs();
        expectedModel.setBook(new GettingAuthorsBooksRs.Book(0, null, null));
        expectedModel.setStatusCode(400);
        expectedModel.setErrorCode(1001);
        expectedModel.setErrorMessage("Не передан id автора");

        shouldBeEquals(actualModel, expectedModel);

    }

}
