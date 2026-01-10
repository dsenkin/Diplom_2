package ru.yandex.practicum.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburgers.data.UserData;
import ru.yandex.practicum.stellarburgers.model.UserModel;
import ru.yandex.practicum.stellarburgers.steps.UserSteps;

import static org.apache.http.HttpStatus.*;
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
    @Description("Проверяем возможность создания курьера при передаче всех полей, проверяем код ответа")
    public void testSuccessCreateUser() {
        userSteps
                .createUser(user)
                .statusCode(SC_OK)
                .body("success", is(true));
    }

    //нельзя создать пользователя, который уже зарегистрирован;
    @Test
    @DisplayName("Проверка неуспешного создания курьера, который уже зарегистрирован, код ответа верный")
    @Description("Проверяем невозможность создания курьера который уже зарегистрирован, проверяем код ответа и сообщение об ошибке")
    public void testUnsuccessCreateExistingUser() {
        userSteps.createUser(user);
        userSteps
                .createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", is("User already exists"));
    }

    //нельзя создать пользователя не заполнив обязательное поле пароль
    @Test
    @DisplayName("Проверка неуспешного создания курьера не заполнив обязательное поле пароль, код ответа верный")
    @Description("Проверяем невозможность создания курьера без пароля, проверяем код ответа и сообщение об ошибке")
    public void testUnsuccessCreateUserWithoutPassword() {
        user.setPassword(null);
        userSteps
                .createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"));
    }

    //нельзя создать пользователя не заполнив обязательное поле имя
    @Test
    @DisplayName("Проверка неуспешного создания курьера не заполнив обязательное поле имя, код ответа верный")
    @Description("Проверяем невозможность создания курьера без имени, проверяем код ответа и сообщение об ошибке")
    public void testUnsuccessCreateUserWithoutName() {
        user.setName(null);
        userSteps
                .createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"));
    }

    //нельзя создать пользователя не заполнив обязательное поле email
    @Test
    @DisplayName("Проверка неуспешного создания курьера не заполнив обязательное поле email, код ответа верный")
    @Description("Проверяем невозможность создания курьера без email, проверяем код ответа и сообщение об ошибке")
    public void testUnsuccessCreateUserWithoutEmail() {
        user.setEmail(null);
        userSteps
                .createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"));
    }

    @After
    public void tearDown() {
        String accessToken = userSteps.getToken(user);
        if (accessToken != null) {
            userSteps.deleteUser(accessToken).statusCode(SC_ACCEPTED);
        }
    }
}
