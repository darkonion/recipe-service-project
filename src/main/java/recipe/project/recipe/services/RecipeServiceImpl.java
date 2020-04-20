package recipe.project.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import recipe.project.recipe.domain.Recipe;
import recipe.project.recipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Inside Service :) :)");
        Set<Recipe> recipeSet = new HashSet<>();
        Iterable<Recipe> all = recipeRepository.findAll();

        all.iterator().forEachRemaining(recipeSet::add);

        return recipeSet;
    }
}
