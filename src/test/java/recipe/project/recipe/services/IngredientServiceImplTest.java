package recipe.project.recipe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import recipe.project.recipe.command.IngredientCommand;
import recipe.project.recipe.converters.IngredientCommandToIngredient;
import recipe.project.recipe.converters.IngredientToIngredientCommand;
import recipe.project.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import recipe.project.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import recipe.project.recipe.domain.Ingredient;
import recipe.project.recipe.domain.Recipe;
import recipe.project.recipe.repositories.RecipeRepository;
import recipe.project.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientCommandToIngredient toIngredient;

    IngredientToIngredientCommand toIngredientCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        toIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientService = new IngredientServiceImpl(toIngredientCommand,
                recipeRepository, toIngredient, unitOfMeasureRepository);
    }

    @Test
    void findByRecipeIdAndId() {

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

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        IngredientCommand byRecipeIdAndId = ingredientService.findByRecipeIdAndId(1L, 3L);

        assertEquals(3L, byRecipeIdAndId.getId());
        assertEquals(1L, byRecipeIdAndId.getRecipeId());
    }

    @Test
    void saveIngredientCommand() {
        Recipe recipe = new Recipe();

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(2L);
        savedRecipe.setId(3L);

        IngredientCommand command = new IngredientCommand();
        command.setId(2L);
        command.setRecipeId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        assertEquals(2L, savedCommand.getId());
        assertEquals(3L, savedCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }
}