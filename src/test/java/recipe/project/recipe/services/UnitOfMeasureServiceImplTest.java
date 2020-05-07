package recipe.project.recipe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import recipe.project.recipe.command.UnitOfMeasureCommand;
import recipe.project.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import recipe.project.recipe.domain.UnitOfMeasure;
import recipe.project.recipe.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository repository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(repository, unitOfMeasureCommand);
    }

    @Test
    void listAllUoms() {

        //given
        Set<UnitOfMeasure> uoms = new HashSet<>();
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(3L);
        uoms.add(uom);

        //when
        when(repository.findAll()).thenReturn(uoms).thenReturn(uoms);

        //then
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUoms();

        assertEquals(1, unitOfMeasureCommands.size());
        assertEquals(3L, unitOfMeasureCommands.iterator().next().getId());

        verify(repository, times(1)).findAll();
    }
}