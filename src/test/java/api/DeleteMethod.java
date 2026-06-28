package api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class DeleteMethod {

    public Response sendDelete(String url, String body) {
        return given()
                .header("Content-Type", "text/plain")
                .body(body)
                .when()
                .delete(url);
    }
}