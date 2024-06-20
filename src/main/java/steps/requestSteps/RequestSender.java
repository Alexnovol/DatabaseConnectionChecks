package steps.requestSteps;

import io.restassured.response.Response;
import models.get.GettingAuthorsBooksRq;
import models.get.GettingAuthorsBooksRs;
import models.get.GettingAuthorsBooksXmlRq;
import models.post.SavingNewAuthorRq;
import models.post.SavingNewBookRq;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class RequestSender {

    public static Response getBooksXmlResponse(GettingAuthorsBooksXmlRq request) {

        return given()
                .spec(RequestBuilder.getBooksXmlSpec(request))
                .when()
                .post();
    }

    public static List<GettingAuthorsBooksRs> getBooksJsonResponse(GettingAuthorsBooksRq request) {
        Response response = given()
                .spec(RequestBuilder.getBooksJsonSpec(request))
                .when()
                .get();

        int statusCode = response.getStatusCode();

        if (statusCode == 200) {

            return response
                    .jsonPath()
                    .getList(".", GettingAuthorsBooksRs.class);
        } else {
            GettingAuthorsBooksRs responseModel = response
                    .as(GettingAuthorsBooksRs.class);
            responseModel.setStatusCode(statusCode);
            List<GettingAuthorsBooksRs> responseList = new ArrayList<>();
            responseList.add(responseModel);

            return responseList;
        }
    }

    public static Response postAuthorResponse(SavingNewAuthorRq request) {

        return given()
                .spec(RequestBuilder.postAuthorSpec(request))
                .when()
                .post();
    }

    public static Response postBookResponse(SavingNewBookRq request) {

        return given()
                .spec(RequestBuilder.postBookSpec(request))
                .when()
                .post();
    }

    public static String getTokenResponse() {

        return given()
                .spec(RequestBuilder.getTokenSpec())
                .when()
                .get()
                .asString()
                .substring(13)
                .replaceAll("\"", "")
                .replaceAll("}", "");
    }
}
