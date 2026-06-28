package tests;

import api.GetMethod;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class GetRequestWoopsTest {

    private final String ENDPOINT = "https://postman-echo.com/get";

    @Test
    public void testGetRequest() {
        Map<String, String> params = new HashMap<>();
        params.put("foo1", "bar1");
        params.put("foo2", "bar2");


        GetMethod getApi = new GetMethod();
        Response response = getApi.sendGet(ENDPOINT, params);

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Статус-код не 200");

        // проверка поля args
        Map<String, String> args = response.jsonPath().getMap("args");
        Assert.assertEquals(args.get("foo1"), "bar1", "Значение параметра 'foo1' неверно");
        Assert.assertEquals(args.get("foo2"), "bar2", "Значение параметра 'foo2' неверно");

        // проверка поля url
        String url = response.jsonPath().getString("url");
        String expectedUrl = "https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        Assert.assertEquals(url, expectedUrl, "URL в ответе не совпадает с ожидаемым");
    }
}