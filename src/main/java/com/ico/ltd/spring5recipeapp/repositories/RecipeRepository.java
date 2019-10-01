package com.ico.ltd.spring5recipeapp.repositories;

import com.ico.ltd.spring5recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
