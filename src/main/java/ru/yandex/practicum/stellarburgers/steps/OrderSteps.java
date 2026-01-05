package ru.yandex.practicum.stellarburgers.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    public static String CREATE_ORDER_ENDPOINT = "/api/orders";

    @Step("Создание заказа с ингридиентами с авторизацией")
    public static ValidatableResponse createOrderWithAuth(List<String> ingredients, String accessToken) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return given()
                .header("Authorization", accessToken)
                .body("{\"ingredients\": " + mapper.writeValueAsString(ingredients)+"}")
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then();
    }

    @Step("Создание заказа с ингридиентами без авторизации")
    public ValidatableResponse createOrderWithoutAuth(ArrayList<String> ingredients){
        return given()
                .body("{\"ingredients\": [\"" + String.join("\", \"", ingredients) + "\"]}")
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then();
    }

    @Step("Создание заказа с авторизацией без ингредиентов")
    public ValidatableResponse createOrderWithAuthWithoutIngredients(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then();
    }

    @Step("Создание заказа с авторизацией с неверным хешем ингредиентов")
    public ValidatableResponse createOrderWithAuthIncorrectIngredients(String wrongIngredients, String accessToken) throws JsonProcessingException {
        return given()
                .header("Authorization", accessToken)
                .body(wrongIngredients)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then();
    }
}
