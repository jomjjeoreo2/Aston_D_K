package tests;

import api.PutMethod;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PutDataTest {

    private final String ENDPOINT = "https://postman-echo.com/put";
    private final String REQUEST_BODY = "This is expected to be sent back as part of response body.";

    @Test(description = "Отправка PUT-запроса с телом (body)")
    public void checkPutBody() {

        PutMethod putApi = new PutMethod();
        Response response = putApi.sendPut(ENDPOINT, REQUEST_BODY);


        System.out.println(" ОТВЕТ СЕРВЕРА НА PUT");
        System.out.println("Статус-код: " + response.getStatusCode());
        System.out.println("Тело ответа: " + response.getBody().asPrettyString());

        // проверка статуса (ожидаем 200)
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Ожидается успешный ответ на PUT-запрос");

        // 4. Проверка тела ответа
        String responseBody = response.jsonPath().getString("data");
        Assert.assertEquals(responseBody, REQUEST_BODY,
                "Тело ответа не совпадает с телом запроса. Отправлено: '" + REQUEST_BODY +
                        "', Получено: '" + responseBody + "'");
    }
}