package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

@Controller
public class RecipeController {

    final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        Recipe recipeReturned = recipeService.findById(id);

        model.addAttribute("recipe", recipeReturned);

        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId();
    }
}
