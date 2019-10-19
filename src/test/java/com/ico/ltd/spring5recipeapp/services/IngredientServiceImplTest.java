package com.ico.ltd.spring5recipeapp.services;

import com.ico.ltd.spring5recipeapp.commands.IngredientCommand;
import com.ico.ltd.spring5recipeapp.converters.IngredientCommandToIngredientConverter;
import com.ico.ltd.spring5recipeapp.converters.IngredientToIngredientCommandConverter;
import com.ico.ltd.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasureConverter;
import com.ico.ltd.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommandConverter;
import com.ico.ltd.spring5recipeapp.domain.Ingredient;
import com.ico.ltd.spring5recipeapp.domain.Recipe;
import com.ico.ltd.spring5recipeapp.repositories.RecipeRepository;
import com.ico.ltd.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    private final IngredientToIngredientCommandConverter toIngredientCommandConverter;

    private final IngredientCommandToIngredientConverter toIngredientConverter;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.toIngredientCommandConverter = new IngredientToIngredientCommandConverter(
                new UnitOfMeasureToUnitOfMeasureCommandConverter()
        );

        this.toIngredientConverter = new IngredientCommandToIngredientConverter(
                new UnitOfMeasureCommandToUnitOfMeasureConverter()
        );
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(toIngredientCommandConverter, toIngredientConverter,
                recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any(Recipe.class));

    }

}