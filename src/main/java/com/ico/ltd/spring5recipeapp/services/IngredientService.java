package com.ico.ltd.spring5recipeapp.services;

import com.ico.ltd.spring5recipeapp.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteByRecipeIdAndIngredientId(String recipeId, String id);
}
