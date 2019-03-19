package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeService recipeService;

    public IngredientServiceImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);

        Object x = recipeCommand.getIngredients();

        Optional<IngredientCommand> ingredientCommandOptional = recipeCommand.getIngredients().stream()
                .filter(ingredientCommand -> ingredientCommand.getId().equals(ingredientId))
                .findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            throw new RuntimeException("Ingredient command not found");
        }

        return ingredientCommandOptional.get();
    }
}
