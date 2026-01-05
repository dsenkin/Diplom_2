package ru.yandex.practicum.stellarburgers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburgers.data.OrderData;
import ru.yandex.practicum.stellarburgers.data.UserData;
import ru.yandex.practicum.stellarburgers.model.OrderModel;
import ru.yandex.practicum.stellarburgers.model.UserModel;
import ru.yandex.practicum.stellarburgers.steps.OrderSteps;
import ru.yandex.practicum.stellarburgers.steps.UserSteps;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTests extends BaseApiTest {
    UserSteps userSteps = new UserSteps();
    private OrderSteps orderSteps = new OrderSteps();
    private UserModel user;
    private OrderModel order;
    String accessToken;

    @Before
    public void setUp() {
        OrderData orderData = new OrderData();
        order = new OrderModel();
        order.setIngredients(orderData.getIngredients());
        order.setWrongIngredients(orderData.getWrongIngredients());

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
    public void createOrderWithAuthTest() throws JsonProcessingException {
        orderSteps.createOrderWithAuth(order.getIngredients(), accessToken)
                .statusCode(200)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с ингридиентами без авторизации")
    public void createOrderWithoutAuthTest() {
        orderSteps.createOrderWithoutAuth(order.getIngredients())
                .statusCode(200)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Невозможно создание заказа без ингредиентов(пользователь авторизован)")
    public void createOrderWithAuthWithoutIngredientsTest() {
        orderSteps.createOrderWithAuthWithoutIngredients(accessToken)
                .statusCode(400)
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Невозможно создать заказ с неверным хешем ингредиентов(пользователь авторизован)")
    public void createOrderWithAuthIncorrectIngredientsTest() throws JsonProcessingException {
        orderSteps.createOrderWithAuthIncorrectIngredients(order.getWrongIngredients() , accessToken)
                .statusCode(500);
    }

    @After
    public void tearDown() {
        String accessToken = userSteps.getToken(user);
        userSteps.deleteUser(accessToken).statusCode(202);
    }
}
