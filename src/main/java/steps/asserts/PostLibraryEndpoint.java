package steps.asserts;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostLibraryEndpoint {

    public static <T> void shouldBeEquals(T actual, T expected) {

        assertEquals(expected, actual);
    }

    public static void checkStatusCode(Response response, int statusCode) {

        response
                .then()
                .statusCode(statusCode);
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
}
