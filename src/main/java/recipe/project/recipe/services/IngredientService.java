package recipe.project.recipe.services;

import recipe.project.recipe.command.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(Long recipeId, Long ingredientId);
}
