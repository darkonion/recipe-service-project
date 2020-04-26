package recipe.project.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RecipeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RecipeApplication.class, args);
    }
}
