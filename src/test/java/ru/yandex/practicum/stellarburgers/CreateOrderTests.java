package ru.yandex.practicum.stellarburgers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburgers.data.OrderData;
import ru.yandex.practicum.stellarburgers.data.UserData;
import ru.yandex.practicum.stellarburgers.model.OrderModel;
import ru.yandex.practicum.stellarburgers.model.UserModel;
import ru.yandex.practicum.stellarburgers.steps.IngredientsSteps;
import ru.yandex.practicum.stellarburgers.steps.OrderSteps;
import ru.yandex.practicum.stellarburgers.steps.UserSteps;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTests extends BaseApiTest {
    UserSteps userSteps = new UserSteps();
    private OrderSteps orderSteps = new OrderSteps();
    private UserModel user;
    private OrderModel order;
    private OrderData orderData;
    private IngredientsSteps ingredientsSteps;

    String accessToken;

    @Before
    public void setUp() {
        orderData = new OrderData();
        order = new OrderModel();

        ingredientsSteps = new IngredientsSteps();

        user = new UserModel();
        UserData userData = new UserData();
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());
        user.setName(userData.getName());

        userSteps.createUser(user);
        accessToken = userSteps.getToken(user);
    }

    @Test
    @DisplayName("Создание заказа с ингридиентами с авторизацией")
    @Description("Проверка успешного создания заказа с произвольными ингредиентами с авторизованным пользователем")
    public void createOrderWithAuthTest() throws JsonProcessingException {
        orderSteps.createOrderWithAuth(ingredientsSteps.getIdRandomIngredient(3), accessToken)
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с ингридиентами без авторизации")
    @Description("Проверка успешного создания заказа с произвольными ингредиентами с неавторизованным пользователем")
    public void createOrderWithoutAuthTest() {
        orderSteps.createOrderWithoutAuth(ingredientsSteps.getIdRandomIngredient(3))
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Невозможно создание заказа без ингредиентов(пользователь авторизован)")
    @Description("Проверка невозможности создания заказа без ингредиентов, пользователь авторизован")
    public void createOrderWithAuthWithoutIngredientsTest() {
        orderSteps.createOrderWithAuthWithoutIngredients(accessToken)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Невозможно создать заказ с неверным хешем ингредиентов(пользователь авторизован)")
    @Description("Проверка невозможности создания заказа с неверным хешем ингредиентов, пользователь авторизован")
    public void createOrderWithAuthIncorrectIngredientsTest() throws JsonProcessingException {
        orderSteps.createOrderWithAuth(orderData.getWrongIngredients(), accessToken)
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void tearDown() {
        String accessToken = userSteps.getToken(user);
        userSteps.deleteUser(accessToken).statusCode(SC_ACCEPTED);
    }
}
