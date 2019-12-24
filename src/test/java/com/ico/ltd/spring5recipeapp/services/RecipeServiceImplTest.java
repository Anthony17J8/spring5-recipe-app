package com.ico.ltd.spring5recipeapp.services;

import com.ico.ltd.spring5recipeapp.converters.RecipeCommandToRecipeConverter;
import com.ico.ltd.spring5recipeapp.converters.RecipeToRecipeCommandConverter;
import com.ico.ltd.spring5recipeapp.domain.Recipe;
import com.ico.ltd.spring5recipeapp.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    private RecipeToRecipeCommandConverter toRecipeCommandConverter;

    @Mock
    private RecipeCommandToRecipeConverter toRecipeConverter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeReactiveRepository, toRecipeCommandConverter, toRecipeConverter);
    }

    @Test
    public void testGetRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);
        when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(recipe));

        List<Recipe> recipes = recipeService.getRecipes().collectList().block();
        assertEquals(1, recipes.size());
        verify(recipeReactiveRepository, times(1)).findAll();
    }

    @Test
    public void testGetRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        Recipe returnedRecipe = recipeService.findById("1").block();

        assertNotNull("Null recipe returned", returnedRecipe);
        verify(recipeReactiveRepository).findById(anyString());
        verify(recipeReactiveRepository, never()).findAll();
    }

    @Test
    public void testDeleteById() throws Exception {
        String id = "1";

        when(recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());
        recipeService.deleteById(id);

        verify(recipeReactiveRepository).deleteById(anyString());
    }
}