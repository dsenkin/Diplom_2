package ru.yandex.practicum.stellarburgers.data;

import java.util.ArrayList;
import java.util.List;

public class OrderData {
    private ArrayList<String> ingredients;
    private String wrongIngredients;

    public OrderData() {
        ingredients = new ArrayList<>();
        ingredients.add("61c0c5a71d1f82001bdaaa6d"); // Флюоресцентная булка R2-D3
        ingredients.add("61c0c5a71d1f82001bdaaa71"); // Биокотлета из марсианской Магнолии
        ingredients.add("61c0c5a71d1f82001bdaaa73"); // Соус фирменный Space Sauce

        wrongIngredients = "{\"ingredients\": [\"65c0c5a71d1f82001bdaah7d\", \"61c0c5a71d1f82001bdaaa71\", \"61c0c5a71d1f82001bdaaa73\"]}";
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getWrongIngredients() {
        return wrongIngredients;
    }
}
