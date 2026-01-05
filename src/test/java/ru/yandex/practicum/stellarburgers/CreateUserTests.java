package ru.yandex.practicum.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburgers.data.UserData;
import ru.yandex.practicum.stellarburgers.model.UserModel;
import ru.yandex.practicum.stellarburgers.steps.UserSteps;
import static org.hamcrest.CoreMatchers.is;

public class CreateUserTests extends BaseApiTest{
    UserSteps userSteps = new UserSteps();
    private UserModel user;

    @Before
    public void setUp() {
        user = new UserModel();
        UserData userData = new UserData();
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());
        user.setName(userData.getName());
    }
    //пользователя можно создать, передаем все поля, код ответа верный;
    @Test
    @DisplayName("Проверка успешного создания курьера при передаче всех полей, код ответа верный")
    public void testSuccessCreateUser() {
        userSteps
                .createUser(user)
                .statusCode(200)
                .body("success", is(true));
    }

    //нельзя создать пользователя, который уже зарегистрирован;
    @Test
    @DisplayName("Проверка неуспешного создания курьера, который уже зарегистрирован, код ответа верный")
    public void testUnsuccessCreateExistingUser() {
        userSteps.createUser(user);
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false));
    }

    //нельзя создать пользователя не заполнив обязательное поле пароль
    @Test
    @DisplayName("Проверка неуспешного создания курьера не заполнив обязательное поле пароль, код ответа верный")
    public void testUnsuccessCreateUserWithoutPassword() {
        user.setPassword(null);
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false));
    }

    //нельзя создать пользователя не заполнив обязательное поле имя
    @Test
    @DisplayName("Проверка неуспешного создания курьера не заполнив обязательное поле имя, код ответа верный")
    public void testUnsuccessCreateUserWithoutName() {
        user.setName(null);
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false));
    }

    //нельзя создать пользователя не заполнив обязательное поле email
    @Test
    @DisplayName("Проверка неуспешного создания курьера не заполнив обязательное поле email, код ответа верный")
    public void testUnsuccessCreateUserWithoutEmail() {
        user.setEmail(null);
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false));
    }

    @After
    public void tearDown() {
        String accessToken = userSteps.getToken(user);
        if (accessToken != null) {
            userSteps.deleteUser(accessToken).statusCode(202);
        }
    }
}
