package recipe.project.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import recipe.project.recipe.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByDescription(String description);
}
