package recipe.project.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import recipe.project.recipe.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
