package recipe.project.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import recipe.project.recipe.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
