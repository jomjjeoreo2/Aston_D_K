package api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class PatchMethod {

    public Response sendPatch(String url, String body) {
        return given()
                .header("Content-Type", "text/plain")
                .body(body)
                .when()
                .patch(url);
    }
}