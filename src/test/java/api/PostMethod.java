package api;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostMethod {

    public Response sendPost(String url, String body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(url);
    }

    public Response sendPostForm(String url, Map<String, String> formData) {
        return given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(formData)
                .when()
                .post(url);
    }
}

