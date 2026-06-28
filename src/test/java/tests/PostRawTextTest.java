package tests;

import api.PostMethod;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class PostRawTextTest {

    private final String BASE_URL = "https://postman-echo.com/post";
    private final String REQUEST_BODY = "{\"test\": \"value\"}";

    @Test(description = "Проверка POST-запроса: статус-код и тело ответа")
    public void checkPostResponse() {
        PostMethod postMethod = new PostMethod();

        Response response = postMethod.sendPost(BASE_URL, REQUEST_BODY);

        System.out.println("ОТВЕТ СЕРВЕРА");
        System.out.println("Статус-код: " + response.getStatusCode());
        System.out.println("Тело ответа: " + response.getBody().asPrettyString());

        // проверки

        // 1. кода ответа
        int statusCode = response.getStatusCode();
        System.out.println("Полученный статус-код: " + statusCode);
        Assert.assertEquals(statusCode, 200, "Ожидался статус-код 200");

        // 2. тело ответа
        String valueOfTestField = response.jsonPath().getString("json.test");
        Assert.assertEquals(valueOfTestField, "value",
                "Значение поля 'test' в ответе неверное.");
    }
}