import java.io.*;
import java.util.*;

public class RecipeBook {

    private List<Recipe> recipes;

    public RecipeBook() {
        recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public List<Recipe> getAllRecipes() {
        return new ArrayList<>(recipes); // returns a copy
    }

    public List<Recipe> findRecipesByIngredient(String ingredient) {
        List<Recipe> res = new ArrayList<>();
        for (Recipe recipe : recipes) {
            for (String ing : recipe.getIngredients()) {
                if (ing.trim().equalsIgnoreCase(ingredient.trim())) {
                    res.add(recipe);
                    break;
                }
            }
        }
        return res;
    }

    public Recipe getRecipeById(int recipeId) {
        for (Recipe recipe : recipes) {
            if (recipe.getRecipeId() == recipeId) {
                return recipe;
            }
        }
        return null;
    }

    public void deleteRecipe(int recipeId) {
        recipes.removeIf(recipe -> recipe.getRecipeId() == recipeId);
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int id = -1;
            String name = null;
            List<String> ingredients = null;
            String instructions = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID:")) {
                    id = Integer.parseInt(line.substring(3).trim());
                } else if (line.startsWith("Name:")) {
                    name = line.substring(5).trim();
                } else if (line.startsWith("Ingredients:")) {
                    String[] parts = line.substring(12).split(",");
                    ingredients = new ArrayList<>();
                    for (String ing : parts) {
                        ingredients.add(ing.trim());
                    }
                } else if (line.startsWith("Instructions:")) {
                    instructions = line.substring(13).trim();
                } else if (line.equals("---")) {
                    if (id != -1 && name != null && ingredients != null && instructions != null) {
                        recipes.add(new Recipe(id, name, ingredients, instructions));
                    }
                    // Reset for next recipe
                    id = -1;
                    name = null;
                    ingredients = null;
                    instructions = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
