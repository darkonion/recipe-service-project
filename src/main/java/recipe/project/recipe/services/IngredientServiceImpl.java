package recipe.project.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import recipe.project.recipe.command.IngredientCommand;
import recipe.project.recipe.converters.IngredientToIngredientCommand;
import recipe.project.recipe.domain.Ingredient;
import recipe.project.recipe.domain.Recipe;
import recipe.project.recipe.repositories.RecipeRepository;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand toIngredientCommand;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand toIngredientCommand,
            RecipeRepository recipeRepository) {
        this.toIngredientCommand = toIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long ingredientId) {

        Optional<Recipe> byId = recipeRepository.findById(recipeId);

        if (!byId.isPresent()) {
            log.error("Recipe with id: " + recipeId + " doesnt exits");
            //error handling
        }

        Recipe recipe = byId.get();

        Set<Ingredient> rc = recipe.getIngredients();

        Optional<IngredientCommand> first = rc.stream()
                .filter(c -> c.getId() == ingredientId)
                .map(c -> toIngredientCommand.convert(c))
                .findFirst();

        return first.orElse(null);
    }
}
