package com.ico.ltd.spring5recipeapp.services;

import com.ico.ltd.spring5recipeapp.commands.IngredientCommand;
import com.ico.ltd.spring5recipeapp.converters.IngredientToIngredientCommandConverter;
import com.ico.ltd.spring5recipeapp.domain.Recipe;
import com.ico.ltd.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommandConverter toIngredientCommandConverter;

    private final RecipeRepository recipeRepository;

    @Autowired
    public IngredientServiceImpl(IngredientToIngredientCommandConverter toIngredientCommandConverter, RecipeRepository recipeRepository) {
        this.toIngredientCommandConverter = toIngredientCommandConverter;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        if (!recipeOpt.isPresent()) {
            // todo impl error handling
            log.error("Recipe id not found : " + recipeId);
        }

        Recipe recipe = recipeOpt.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(toIngredientCommandConverter::convert).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            // todo impl error handling
            log.error("Ingredient id not found : " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }
}
