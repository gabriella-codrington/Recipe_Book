import java.util.ArrayList;
import java.util.List;

public class RecipeBook {

    private List<Recipe> recipes;

    public RecipeBook() {
        recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe){
        recipes.add(recipe);
    }

    public List<Recipe> getAllRecipes(){
        return new ArrayList<>(recipes); //returns a copy
    }

    public List<Recipe> findRecipesByIngredient(String ingredient){
        List<Recipe> res = new ArrayList<>();
        for (Recipe recipe: recipes){
            for (String ing: recipe.getIngredients()){
                if(ing.trim().equalsIgnoreCase(ingredient.trim())){
                    res.add(recipe);
                    break;
                }
            }
        }
        return res;
    }

    //get recipe by id
    public Recipe getRecipeById(int recipeId){
        for(Recipe recipe: recipes){
            if(recipe.getRecipeId() == recipeId){
                return recipe;
            }
        }
        return null;
    }

    public void deleteRecipe(int recipeId){
        recipes.removeIf(recipe -> recipe.getRecipeId() == recipeId);
    }

}
