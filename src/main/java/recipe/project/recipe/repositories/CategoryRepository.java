package recipe.project.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import recipe.project.recipe.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
