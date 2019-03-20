package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    @Test
    public void run() {

    }

    @Mock
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    IngredientCommandToIngredient ingredientCommandToIngredient;

    IngredientService ingredientService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(recipeService, recipeRepository, unitOfMeasureRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
    }

    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(1L);
        ingredientCommand.setId(2L);

        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand2.setRecipeId(1L);
        ingredientCommand2.setId(3L);

        recipeCommand.getIngredients().add(ingredientCommand);
        recipeCommand.getIngredients().add(ingredientCommand2);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        IngredientCommand ingredientCommandReturned = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //then
        assertNotNull(ingredientCommandReturned);
        assertEquals(Long.valueOf(1L), ingredientCommandReturned.getRecipeId());
        assertEquals(Long.valueOf(3L), ingredientCommandReturned.getId());
        verify(recipeService).findCommandById(anyLong());
    }
}