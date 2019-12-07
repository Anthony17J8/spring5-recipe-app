package com.ico.ltd.spring5recipeapp.repositories.reactive;

import com.ico.ltd.spring5recipeapp.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
