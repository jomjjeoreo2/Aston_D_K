package tests;

import api.PatchMethod;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PatchDataTest {

    private final String ENDPOINT = "https://postman-echo.com/patch";
    private final String REQUEST_BODY = "This is expected to be sent back as part of response body.";

    @Test(description = "Отправка PATCH-запроса с телом (body)")
    public void checkPatchBody() {

        PatchMethod patchApi = new PatchMethod();
        Response response = patchApi.sendPatch(ENDPOINT, REQUEST_BODY);

        System.out.println("ОТВЕТ СЕРВЕРА НА PATCH");
        System.out.println("Статус-код: " + response.getStatusCode());
        System.out.println("Тело ответа: " + response.getBody().asPrettyString());


        // проверка статуса (ожидаем 200)
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Ожидается успешный ответ на PATCH-запрос");

        // проверка тела ответа
        String responseBody = response.jsonPath().getString("data");
        Assert.assertEquals(responseBody, REQUEST_BODY,
                "Тело ответа не совпадает с телом запроса. Отправлено: '" + REQUEST_BODY +
                        "', Получено: '" + responseBody + "'");
    }
}