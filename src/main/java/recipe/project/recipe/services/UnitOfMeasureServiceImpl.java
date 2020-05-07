package recipe.project.recipe.services;

import org.springframework.stereotype.Service;
import recipe.project.recipe.command.UnitOfMeasureCommand;
import recipe.project.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import recipe.project.recipe.repositories.UnitOfMeasureRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository repository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository repository,
            UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand) {
        this.repository = repository;
        this.unitOfMeasureCommand = unitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {

        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(unitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
