package com.ico.ltd.spring5recipeapp.services;

import com.ico.ltd.spring5recipeapp.converters.RecipeCommandToRecipeConverter;
import com.ico.ltd.spring5recipeapp.converters.RecipeToRecipeCommandConverter;
import com.ico.ltd.spring5recipeapp.domain.Recipe;
import com.ico.ltd.spring5recipeapp.exceptions.NotFoundException;
import com.ico.ltd.spring5recipeapp.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
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
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeToRecipeCommandConverter toRecipeCommandConverter;

    @Mock
    private RecipeCommandToRecipeConverter toRecipeConverter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, toRecipeCommandConverter, toRecipeConverter);
    }

    @Test
    public void testGetRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testGetRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        Recipe returnedRecipe = recipeService.findById("1");

        assertNotNull("Null recipe returned", returnedRecipe);
        verify(recipeRepository).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void testDeleteById() throws Exception {
        String id = "1";

        recipeService.deleteById(id);

        verify(recipeRepository).deleteById(anyString());
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdTestNotFound() throws Exception {
        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        recipeService.findById("1");
    }
}