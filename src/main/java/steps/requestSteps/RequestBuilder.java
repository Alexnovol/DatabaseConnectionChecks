package steps.requestSteps;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.get.GettingAuthorsBooksRq;
import models.get.GettingAuthorsBooksXmlRq;
import models.get.GettingToken;
import models.post.SavingNewAuthorRq;
import models.post.SavingNewBookRq;

public class RequestBuilder {

    public static RequestSpecBuilder commonSpecBuilder() {

        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080/library")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + RequestSender.getTokenResponse())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter());
    }

    public static RequestSpecification getBooksXmlSpec(GettingAuthorsBooksXmlRq request) {

        return commonSpecBuilder()
                .setBasePath("authors/books")
                .setContentType(ContentType.XML)
                .setBody(request)
                .build();
    }

    public static RequestSpecification getBooksJsonSpec(GettingAuthorsBooksRq request) {

        return commonSpecBuilder()
                .setBasePath(String.format("authors/%s/books", request.getId()))
                .build();
    }

    public static RequestSpecification postAuthorSpec(SavingNewAuthorRq request) {

        return commonSpecBuilder()
                .setBasePath("authors/save")
                .setBody(request)
                .build();
    }

    public static RequestSpecification postBookSpec(SavingNewBookRq request) {

        return commonSpecBuilder()
                .setBasePath("books/save")
                .setBody(request)
                .build();
    }

    public static RequestSpecification getTokenSpec() {
        GettingToken request = new GettingToken("master_log", "qweasdzxc");

        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080")
                .setBasePath("auth/login")
                .setContentType(ContentType.JSON)
                .setBody(request)
                .build();
    }


}
