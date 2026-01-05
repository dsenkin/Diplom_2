package ru.yandex.practicum.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburgers.data.UserData;
import ru.yandex.practicum.stellarburgers.model.UserModel;
import ru.yandex.practicum.stellarburgers.steps.UserSteps;

import static org.hamcrest.CoreMatchers.is;

public class LoginUserTests extends BaseApiTest{
    UserSteps userSteps = new UserSteps();
    private UserModel user;

    @Before
    public void setUp() {
        user = new UserModel();
        UserData userData = new UserData();
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());
        user.setName(userData.getName());

        userSteps.createUser(user);
    }

    //    вход под существующим пользователем;
    @Test
    @DisplayName("Проверка успешного входа существующего курьера, код ответа верный")
    public void successfullyLoginUserTest(){
        userSteps.loginUser(user)
                .statusCode(200)
                .body("success", is(true));
    }

    //    вход с неверным логином и паролем.
    @Test
    @DisplayName("Проверка невозможности входа курьера с неверным логином и паролем, код ответа верный")
    public void unsuccessfullyLoginUserWithWrongEmailAndPasswordTest(){
        String correctEmail = user.getEmail();
        String correctPassword = user.getPassword();
        user.setEmail(RandomStringUtils.randomAlphabetic(5)+"@"+RandomStringUtils.randomAlphabetic(5)+".ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(10));

        userSteps.loginUser(user)
                .statusCode(401)
                .body("success", is(false));

        user.setEmail(correctEmail);
        user.setPassword(correctPassword);
    }

    //    вход с неверным логином.
    @Test
    @DisplayName("Проверка невозможности входа курьера с неверным логином, код ответа верный")
    public void unsuccessfullyLoginUserWithWrongEmailTest(){
        String correctEmail = user.getEmail();
        user.setEmail(RandomStringUtils.randomAlphabetic(5)+"@"+RandomStringUtils.randomAlphabetic(5)+".ru");

        userSteps.loginUser(user)
                .statusCode(401)
                .body("success", is(false));

        user.setEmail(correctEmail);
    }

    //    вход с неверным паролем.
    @Test
    @DisplayName("Проверка невозможности входа курьера с неверным паролем, код ответа верный")
    public void unsuccessfullyLoginUserWithWrongPasswordTest(){
        String correctPassword = user.getPassword();
        user.setPassword(RandomStringUtils.randomAlphabetic(10));

        userSteps.loginUser(user)
                .statusCode(401)
                .body("success", is(false));

        user.setPassword(correctPassword);
    }

    @After
    public void tearDown() {
        String accessToken = userSteps.getToken(user);
        userSteps.deleteUser(accessToken).statusCode(202);
    }
}
