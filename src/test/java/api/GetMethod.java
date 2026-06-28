package api;

import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class GetMethod {

    public Response sendGet(String url, Map<String, ?> params) {
        return given()
                .queryParams(params)
                .when()
                .get(url);
    }
}