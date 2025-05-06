import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;

public class SearchGUI extends JFrame {
    private JTextField searchIngredients;
    private JButton searchButton;
    private JButton viewButton;
    private JButton cancelButton;
    private JList<String> resultList;
    private JScrollPane resultScrollPane;
    
    private RecipeBook recipeBook;

    private List<Recipe> lastSearchResults = new ArrayList<>();


    public SearchGUI(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
      
        resultList = new JList<>();
        resultScrollPane = new JScrollPane(resultList); 

        setTitle("Search Recipe");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        formPanel.add(new JLabel("Search Recipe (comma-separated ingredients):"));
        searchIngredients = new JTextField();
        formPanel.add(searchIngredients);

        add(formPanel, BorderLayout.NORTH);

        resultScrollPane.setBorder(BorderFactory.createTitledBorder("Matching Recipes"));

        add(resultScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchButton = new JButton("Search");
        viewButton = new JButton("View");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(searchButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchRecipe(e));

        viewButton.addActionListener(e -> {
            int selectedIndex = resultList.getSelectedIndex();
            if (selectedIndex != -1 && selectedIndex < lastSearchResults.size()) {
                Recipe selectedRecipe = lastSearchResults.get(selectedIndex);
                selectRecipe(selectedRecipe.getRecipeId());
            } else {
                JOptionPane.showMessageDialog(this, "Please select a recipe to view.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }
    public void searchRecipe(ActionEvent e) {
        String input = searchIngredients.getText().trim().toLowerCase();
        if (input.isEmpty()) {
            resultList.setListData(new String[0]);
            return;
        }
    
        String[] ingredientsToSearch = input.split(",");
        List<Recipe> filteredRecipes = recipeBook.getAllRecipes();
    
        for (String ingredient : ingredientsToSearch) {
            String trimmed = ingredient.trim();
            filteredRecipes = filteredRecipes.stream()
                .filter(recipe -> recipe.getIngredients().stream()
                    .anyMatch(ing -> ing.toLowerCase().contains(trimmed)))
                .toList();
        }
    
        lastSearchResults = filteredRecipes;
    
        List<String> recipeNames = filteredRecipes.stream()
            .map(Recipe::getName)
            .toList();
    
        resultList.setListData(recipeNames.toArray(new String[0]));
    }
    
    public void selectRecipe(int recipeID) {
        Recipe recipe = recipeBook.getRecipeById(recipeID);
        if (recipe != null) {
            StringBuilder details = new StringBuilder();
            details.append("ID: ").append(recipe.getRecipeId()).append("\n");
            details.append("Name: ").append(recipe.getName()).append("\n");
            details.append("Ingredients: ").append(String.join(", ", recipe.getIngredients())).append("\n");
            details.append("Instructions: ").append(recipe.getInstructions()).append("\n");
            details.append("Rating: ").append(recipe.getRating());

            
            JOptionPane.showMessageDialog(this, details.toString(), "Recipe Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Recipe not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}