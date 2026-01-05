package ru.yandex.practicum.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.stellarburgers.model.UserModel;
import static io.restassured.RestAssured.given;

public class UserSteps {
    public static String CREATE_USER_ENDPOINT = "/api/auth/register";
    public static String LOGIN_USER_ENDPOINT = "/api/auth/login";
    public static String DELETE_USER_ENDPOINT = "/api/auth/user";

    @Step ("Create User")
    public static ValidatableResponse createUser(UserModel user){
        return given()
                .body(user)
                .when()
                .post(CREATE_USER_ENDPOINT)
                .then();
    }

    @Step//("User Login")
    public static ValidatableResponse loginUser(UserModel user){
        return given()
                .body(user)
                .when().post(LOGIN_USER_ENDPOINT)
                .then();
    }
    @Step ("Delete User")
    public static ValidatableResponse deleteUser(String accessToken) {
        if (accessToken != null) {
            return given()
                    .header("Authorization", accessToken)
                    .when()
                    .delete(DELETE_USER_ENDPOINT)
                    .then();
        }
        return null;
    }
    @Step ("Get token")
    public static String getToken(UserModel user) {
        String accessToken = null;
        ValidatableResponse vResponse = loginUser(user);

        if (vResponse.extract().statusCode() == 200) {
            accessToken = vResponse.extract().body().path("accessToken");
        }

        return accessToken;
    }
}