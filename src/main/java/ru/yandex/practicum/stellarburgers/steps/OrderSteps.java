package ru.yandex.practicum.stellarburgers.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.stellarburgers.model.OrderModel;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    public static String CREATE_ORDER_ENDPOINT = "/api/orders";
    OrderModel order = new OrderModel();

    @Step("Создание заказа с ингридиентами с авторизацией")
    public ValidatableResponse createOrderWithAuth(List<String> ingredientsId, String accessToken) throws JsonProcessingException {
        order.setIngredients(ingredientsId);
        return given()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then();
    }

    @Step("Создание заказа с ингридиентами без авторизации")
    public ValidatableResponse createOrderWithoutAuth(List<String> ingredientsId){
        order.setIngredients(ingredientsId);
        return given()
                .body(order)
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
    public ValidatableResponse createOrderWithAuthIncorrectIngredients(List<String> wrongIngredientsId, String accessToken) throws JsonProcessingException {
        order.setIngredients(wrongIngredientsId);
        return given()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then();
    }
}
