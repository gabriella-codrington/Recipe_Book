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

    //needs savetofile method

    public void loadFromFile(String filename) {
        recipes.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String name = "";
            List<String> ingredients = new ArrayList<>();
            String instructions = "";
            String time = "";
            String dietType = "";
            Double rating = 0.0;
    
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name:")) {
                    name = line.substring(5).trim();
                } else if (line.startsWith("Ingredients:")) {
                    String[] ings = line.substring(12).split(",");
                    ingredients = Arrays.asList(ings);
                } else if (line.startsWith("Instructions:")) {
                    instructions = line.substring(13).trim();
                } else if (line.startsWith("Time:")) {
                    time = line.substring(13).trim();
                } else if (line.startsWith("Diet Type:")) {
                    dietType = line.substring(10).trim();
                } else if (line.startsWith("Rating:")) {
                    rating = Double.parseDouble(line.substring(7).trim());
                } else if (line.equals("---")) {
                    Recipe recipe = new Recipe(name, ingredients, instructions, time, dietType);
                    recipe.setRating(rating); // If rating is optional/set separately
                    recipes.add(recipe);
    
                    // reset temp variables
                    name = "";
                    ingredients = new ArrayList<>();
                    instructions = "";
                    dietType = "";
                    rating = 0.0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Recipe recipe : recipes) {
                writer.println("ID:" + recipe.getRecipeId());
                writer.println("Name:" + recipe.getName());
                writer.println("Ingredients:" + String.join(",", recipe.getIngredients()));
                writer.println("Instructions:" + recipe.getInstructions());
                writer.println("Diet Type:" + recipe.getDietType());
                writer.println("Rating:" + recipe.getRating());
                writer.println("---");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
