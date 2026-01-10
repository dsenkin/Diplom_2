package ru.yandex.practicum.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class IngredientsSteps {
    private static final String GET_INGREDIENTS = "/api/ingredients";

    @Step("Получение списка всех возможных ингредиентов")
    public ValidatableResponse getAllIngredients() {
        return given()
                .when()
                .get(GET_INGREDIENTS)
                .then();
    }

    @Step("Получение списка ID всех возможных ингредиентов")
    public List<String> getIdAllIngredients() {
        return getAllIngredients()
                .extract()
                .path("data._id");
    }

    @Step("Получение ID нескольких случайных ингредиентов")
    public List<String> getIdRandomIngredient(int count) {
        return getIdAllIngredients().stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                    Collections.shuffle(collected);
                    return collected.stream();
                }))
                .limit(count)
                .collect(Collectors.toList());
    }
}