package com.cp.georgianasas.domain;

public class  Recipe {
    private int id;
    private String name;
    private int cookingTime;
    private String ingredients;

    public Recipe(int id, String name, int cookingTime, String ingredients) {
        this.id = id;
        this.name = name;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCookingTime() { return cookingTime; }
    public String getIngredients() { return ingredients; }

    public int getIngredientCount() {
        if (ingredients == null || ingredients.isEmpty()) return 0;
        return ingredients.split(";").length;
    }
}
