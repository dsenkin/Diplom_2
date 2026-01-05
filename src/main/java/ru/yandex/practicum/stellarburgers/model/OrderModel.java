package ru.yandex.practicum.stellarburgers.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderModel {
    private ArrayList<String> ingredients;
    private String wrongIngredients;

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
    public String getWrongIngredients() {
        return wrongIngredients;
    }
    public void setWrongIngredients(String wrongIngredients) {
        this.wrongIngredients = wrongIngredients;
    }
}
