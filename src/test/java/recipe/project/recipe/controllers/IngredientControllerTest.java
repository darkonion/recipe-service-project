package recipe.project.recipe.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import recipe.project.recipe.command.IngredientCommand;
import recipe.project.recipe.command.RecipeCommand;
import recipe.project.recipe.command.UnitOfMeasureCommand;
import recipe.project.recipe.services.IngredientService;
import recipe.project.recipe.services.RecipeService;
import recipe.project.recipe.services.UnitOfMeasureService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    MockMvc mockMvc;

    IngredientController ingredientController;

    @BeforeEach
    void setUp() {
        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void listIngredients() throws Exception {

        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    void showIngredient() throws Exception {

        IngredientCommand ic = new IngredientCommand();
        when(ingredientService.findByRecipeIdAndId(anyLong(),anyLong()))
                .thenReturn(ic);

        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1))
                .findByRecipeIdAndId(anyLong(), anyLong());
    }


    @Test
    void updateRecipeIngredient() throws Exception {
        IngredientCommand ic = new IngredientCommand();
        Set<UnitOfMeasureCommand> uoms = new HashSet<>();

        when(ingredientService.findByRecipeIdAndId(anyLong(), anyLong())).thenReturn(ic);
        when(unitOfMeasureService.listAllUoms()).thenReturn(uoms);

        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .equals(model().attributeExists("ingredient", "uomList"));

        verify(ingredientService, times(1)).findByRecipeIdAndId(anyLong(), anyLong());
        verify(unitOfMeasureService, times(1)).listAllUoms();

    }

    @Test
    void saveOrUpdate() throws Exception {
        IngredientCommand ic = new IngredientCommand();
        ic.setId(2L);
        ic.setRecipeId(4L);

        when(ingredientService.saveIngredientCommand(any())).thenReturn(ic);

        mockMvc.perform(post("/recipe/1/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/4/ingredient/2/show"));

        verify(ingredientService, times(1)).saveIngredientCommand(any());


    }
}