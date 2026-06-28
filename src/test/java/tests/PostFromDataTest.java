package tests;

import api.PostMethod;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class PostFromDataTest {

    private final String ENDPOINT = "https://postman-echo.com/post";

    @Test(description = "Отправка POST-запроса с формой (x-www-form-urlencoded)")
    public void checkPostFormData() {

        Map<String, String> formData = new HashMap<>();
        formData.put("foo1", "bar1");
        formData.put("foo2", "bar2");

        PostMethod postApi = new PostMethod();
        Response response = postApi.sendPostForm(ENDPOINT, formData);

        System.out.println("ОТВЕТ СЕРВЕРА");
        System.out.println("Статус-код: " + response.getStatusCode());
        System.out.println("Тело ответа: " + response.getBody().asPrettyString());

        //  статус (ожидаем 200)
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Ожидается успешный ответ. Получен статус: " + statusCode);

        // тело ответа ( если статус 200)
        JsonPath json = response.jsonPath();
        Map<String, String> formResponse = json.getMap("form");

        Assert.assertEquals(formResponse.get("foo1"), "bar1", "Значение foo1 не совпадает");
        Assert.assertEquals(formResponse.get("foo2"), "bar2", "Значение foo2 не совпадает");
    }
}