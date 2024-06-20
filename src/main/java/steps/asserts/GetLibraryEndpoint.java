package steps.asserts;

import io.restassured.response.Response;
import models.get.GettingAuthorsBooksRs;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetLibraryEndpoint {

    public static <T> void shouldBeEquals(T actual, T expected) {

        assertEquals(expected, actual);
    }

    public static void checkStatusCode(Response response, int statusCode) {

        response
                .then()
                .statusCode(statusCode);
    }

    public static void checkStatusCodeGetBooksJson(GettingAuthorsBooksRs actualModel, int statusCode) {

        assertEquals(statusCode, actualModel.getStatusCode());
    }

    public static void commonErrorMessageShouldBeEquals(Response response, int code, String message) {

        response
                .then()
                .assertThat()
                .body("errorCode", equalTo(code))
                .body("$", hasKey("errorDetails"));

        if (message != null) {

            response
                    .then()
                    .assertThat()
                    .body("errorMessage", equalTo(message));
        } else {

            response
                    .then()
                    .assertThat()
                    .body("$", hasKey("errorMessage"));
        }
    }

    public static void shouldConformTemplate(List<String> updatedList) {
        List<String> filteredList = updatedList
                .stream()
                .filter(updated -> updated.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+\\+00:00"))
                .toList();

        assertEquals(updatedList, filteredList);
    }
}
