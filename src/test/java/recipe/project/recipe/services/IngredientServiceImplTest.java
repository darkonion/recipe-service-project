package recipe.project.recipe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import recipe.project.recipe.command.IngredientCommand;
import recipe.project.recipe.converters.IngredientToIngredientCommand;
import recipe.project.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import recipe.project.recipe.domain.Ingredient;
import recipe.project.recipe.domain.Recipe;
import recipe.project.recipe.repositories.RecipeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    IngredientToIngredientCommand toIngredientCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(toIngredientCommand, recipeRepository);
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
}