package com.ico.ltd.spring5recipeapp.services;

import com.ico.ltd.spring5recipeapp.commands.RecipeCommand;
import com.ico.ltd.spring5recipeapp.converters.RecipeCommandToRecipeConverter;
import com.ico.ltd.spring5recipeapp.converters.RecipeToRecipeCommandConverter;
import com.ico.ltd.spring5recipeapp.domain.Recipe;
import com.ico.ltd.spring5recipeapp.exceptions.NotFoundException;
import com.ico.ltd.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeCommandToRecipeConverter toRecipeConverter;

    private final RecipeToRecipeCommandConverter toRecipeCommandConverter;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeToRecipeCommandConverter toRecipeCommandConverter,
                             RecipeCommandToRecipeConverter toRecipeConverter) {
        this.recipeRepository = recipeRepository;
        this.toRecipeConverter = toRecipeConverter;
        this.toRecipeCommandConverter = toRecipeCommandConverter;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {

        Optional<Recipe> optRecipe = recipeRepository.findById(id);

        if (!optRecipe.isPresent()) {
            throw new NotFoundException("Recipe Not Found. For ID value: " + id);
        }

        return optRecipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = toRecipeConverter.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return toRecipeCommandConverter.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return toRecipeCommandConverter.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
