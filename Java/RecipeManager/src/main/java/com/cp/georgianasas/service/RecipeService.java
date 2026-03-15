package com.cp.georgianasas.service;

import com.cp.georgianasas.domain.Recipe;
import com.cp.georgianasas.repository.RecipeRepository;

import java.util.*;
import java.util.stream.Collectors;

public class RecipeService {

    private RecipeRepository repository = new RecipeRepository();

    public List<Recipe> getAllSorted() {
        List<Recipe> recipes = repository.getAll();
        recipes.sort(Comparator.comparingInt(Recipe::getIngredientCount)
                .thenComparing(Recipe::getName));
        return recipes;
    }

    public void addRecipe(String name, int time, String ingredients) throws Exception {
        if (repository.existsByName(name)) {
            throw new Exception("O reteta cu acest nume exista deja!");
        }
        if (time <= 0) {
            throw new Exception("Durata de gatire trebuie sa fie un numar intreg pozitiv!");
        }
        Recipe recipe = new Recipe(0, name, time, ingredients);
        repository.add(recipe);
    }

    public List<Recipe> filterRecipes(String searchText) {
        List<Recipe> all = getAllSorted();
        if (searchText == null || searchText.trim().isEmpty()) {
            return all;
        }

        String[] searchIngredients = searchText.split(";");
        return all.stream().filter(r -> {
            String recipeIngrLower = r.getIngredients().toLowerCase();
            for (String searchItem : searchIngredients) {
                if (!recipeIngrLower.contains(searchItem.trim().toLowerCase())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    public Set<String> generateShoppingList(List<Recipe> selectedRecipes) {
        Set<String> uniqueIngredients = new TreeSet<>();
        for (Recipe r : selectedRecipes) {
            String[] parts = r.getIngredients().split(";");
            for (String p : parts) {
                uniqueIngredients.add(p.trim());
            }
        }
        return uniqueIngredients;
    }
}