package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

            if (recipeOptional.isPresent()) {
                Recipe recipe = recipeOptional.get();

                Byte[] bytes = new Byte[file.getBytes().length];

                // CONVERT PRIMITIVE VALUE TO WRAP OBJECT
                int i = 0;
                for (byte b : file.getBytes()) {
                    bytes[i++] = b;
                }

                recipe.setImage(bytes);

                recipeRepository.save(recipe);

            } else {
                log.error("Not found recipe. Id: " + recipeId);
            }
        } catch (IOException e) {
            log.error("Error occurred: ", e);

            e.printStackTrace();
        }
    }
}
