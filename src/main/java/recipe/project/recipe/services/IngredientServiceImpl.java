package recipe.project.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import recipe.project.recipe.command.IngredientCommand;
import recipe.project.recipe.converters.IngredientCommandToIngredient;
import recipe.project.recipe.converters.IngredientToIngredientCommand;
import recipe.project.recipe.domain.Ingredient;
import recipe.project.recipe.domain.Recipe;
import recipe.project.recipe.repositories.RecipeRepository;
import recipe.project.recipe.repositories.UnitOfMeasureRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand toIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final IngredientCommandToIngredient toIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand toIngredientCommand,
            RecipeRepository recipeRepository,
            IngredientCommandToIngredient toIngredient,
            UnitOfMeasureRepository unitOfMeasureRepository) {
        this.toIngredientCommand = toIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.toIngredient = toIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("recipe id not found. Id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
            //todo
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
            } else {
                recipe.addIngredient(toIngredient.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            return toIngredientCommand.convert(savedRecipe.getIngredients()
                    .stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst()
                    .get());
        }
    }
}
