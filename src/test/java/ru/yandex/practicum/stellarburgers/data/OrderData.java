package ru.yandex.practicum.stellarburgers.data;

import java.util.ArrayList;
import java.util.List;

public class OrderData {
    private List<String> wrongIngredients;

    public OrderData() {
        wrongIngredients = new ArrayList<>();
        wrongIngredients.add("65c0c5a71d1f82001bdaah7d");
        wrongIngredients.add("61c0c5a71d1f82001bdaaa71");
        wrongIngredients.add("61c0c5a71d1f82001bdaaa73");
    }

    public List<String> getWrongIngredients() {
        return wrongIngredients;
    }
}
