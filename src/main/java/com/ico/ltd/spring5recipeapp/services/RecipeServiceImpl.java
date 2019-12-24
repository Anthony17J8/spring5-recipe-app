package com.ico.ltd.spring5recipeapp.services;

import com.ico.ltd.spring5recipeapp.commands.RecipeCommand;
import com.ico.ltd.spring5recipeapp.converters.RecipeCommandToRecipeConverter;
import com.ico.ltd.spring5recipeapp.converters.RecipeToRecipeCommandConverter;
import com.ico.ltd.spring5recipeapp.domain.Recipe;
import com.ico.ltd.spring5recipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;

    private final RecipeCommandToRecipeConverter toRecipeConverter;

    private final RecipeToRecipeCommandConverter toRecipeCommandConverter;

    @Autowired
    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository,
                             RecipeToRecipeCommandConverter toRecipeCommandConverter,
                             RecipeCommandToRecipeConverter toRecipeConverter) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.toRecipeConverter = toRecipeConverter;
        this.toRecipeCommandConverter = toRecipeCommandConverter;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeReactiveRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
     return recipeReactiveRepository.save(toRecipeConverter.convert(command))
             .map(toRecipeCommandConverter::convert);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return recipeReactiveRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = toRecipeCommandConverter.convert(recipe);
                    recipeCommand.getIngredients()
                            .forEach(ingredientCommand -> ingredientCommand.setRecipeId(recipeCommand.getId()));
                    return recipeCommand;
                });
    }

    @Override
    public void deleteById(String id) {
        recipeReactiveRepository.deleteById(id).block();
    }
}
