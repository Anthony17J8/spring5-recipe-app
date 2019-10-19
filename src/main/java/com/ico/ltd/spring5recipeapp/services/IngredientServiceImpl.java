package com.ico.ltd.spring5recipeapp.services;

import com.ico.ltd.spring5recipeapp.commands.IngredientCommand;
import com.ico.ltd.spring5recipeapp.converters.IngredientCommandToIngredientConverter;
import com.ico.ltd.spring5recipeapp.converters.IngredientToIngredientCommandConverter;
import com.ico.ltd.spring5recipeapp.domain.Ingredient;
import com.ico.ltd.spring5recipeapp.domain.Recipe;
import com.ico.ltd.spring5recipeapp.repositories.RecipeRepository;
import com.ico.ltd.spring5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommandConverter toIngredientCommandConverter;

    private final IngredientCommandToIngredientConverter toIngredientConverter;

    private final RecipeRepository recipeRepository;

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public IngredientServiceImpl(IngredientToIngredientCommandConverter toIngredientCommandConverter,
                                 IngredientCommandToIngredientConverter toIngredientConverter,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.toIngredientCommandConverter = toIngredientCommandConverter;
        this.toIngredientConverter = toIngredientConverter;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(toIngredientConverter.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //to do check for fail
            return toIngredientCommandConverter.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst()
                    .get());
        }

    }
}
