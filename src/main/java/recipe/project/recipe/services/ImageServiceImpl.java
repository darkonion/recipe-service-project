package recipe.project.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import recipe.project.recipe.domain.Recipe;
import recipe.project.recipe.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository repository;

    public ImageServiceImpl(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {

        Optional<Recipe> recipeOpt = repository.findById(id);

        if (recipeOpt.isPresent()) {
            Recipe recipe = recipeOpt.get();
            try {
                Byte[] copyFile = new Byte[file.getBytes().length];

                int i = 0;
                for (byte b : file.getBytes()) {
                    copyFile[i] = b;
                    i++;
                }
                recipe.setImage(copyFile);
            } catch (IOException e) {
                //todo better
                log.error("Some IO error occurred!");
                e.printStackTrace();
            }
            repository.save(recipe);
        }
    }
}
