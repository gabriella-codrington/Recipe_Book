import java.util.ArrayList;
import java.util.List;


public class Recipe {
    //give unique id to each recipe
    private static int nextRecipeId = 1;

    private int recipeId;
    private String name;
    private List<String> ingredients; 
    private String instructions;
    private String time;
    private Double rating;
    private String dietType;

    public Recipe(String name,List<String> ingredients, String instructions,String time, String dietType){
        this.recipeId = nextRecipeId++;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.time = time;
        this.dietType = dietType;
        this.rating = 0.0;
    }

    public void updateRecipe(String name,List<String> ingredients, String instructions,String time, String dietType){
        this.name = name;
        this.ingredients = new ArrayList<>(ingredients);
        this.instructions = instructions;
        this.time = time;
        this.dietType = dietType;

    }

    public void addRating(double newRating){
        if (newRating >= 0.0 && newRating <= 5.0){
            this.rating = newRating;
        } else {
            System.out.println("The rating needs to be between 0 and 5");
        }
    }

    public void delete(){
        System.out.println("Recipe was choosen for deletion");
    }

    //creating the setters and getters
    public void setName(String name) {this.name = name;}
    public void setIngredients(List<String> ingredients) {this.ingredients = ingredients;}
    public void setInstructions(String instructions) {this.instructions = instructions;}
    public void setTime(String time) {this.time = time;}
    public void setDietType(String dietType) {this.dietType = dietType;}
    public void setRating(double rating) {this.rating = rating;}
    

    public int getRecipeId() {return recipeId;}
    public String getName() {return name;}
    public List<String> getIngredients() {return ingredients;}
    public String getInstructions() {return instructions;}
    public String getTime() {return time;}
    public double getRating() {return rating;}
    public String getDietType() {return dietType;}


    
}
