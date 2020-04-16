package recipe.project.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import recipe.project.recipe.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
}
