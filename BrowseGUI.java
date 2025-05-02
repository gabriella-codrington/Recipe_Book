import javax.swing.*;
import java.awt.*;


public class BrowseGUI extends JFrame {
    private JList<String> recipeList;
    private JScrollPane browseScrollPane;
    private JButton viewButton;
    private JButton modifyButton;
    private JButton rateButton;
    private JButton deleteButton;
    private JButton cancelButton;
 

    private RecipeBook recipeBook;

    public BrowseGUI(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;

        recipeList = new JList<>();
        browseScrollPane = new JScrollPane(recipeList);

        setTitle("Browse Recipes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // List in the center
        add(browseScrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewButton = new JButton("View");
        modifyButton = new JButton("Modify");
        rateButton = new JButton("Rate");
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");
        
        buttonPanel.add(viewButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(rateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Populate the list
        displayRecipes();

        viewButton.addActionListener(e -> {
            int selectedIndex = recipeList.getSelectedIndex();
            if (selectedIndex != -1) {
                Recipe selectedRecipe = recipeBook.getAllRecipes().get(selectedIndex);
                selectRecipe(selectedRecipe.getRecipeId());
            } else {
                JOptionPane.showMessageDialog(this, "Please select a recipe to view.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });

        
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    public void displayRecipes() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Recipe recipe : recipeBook.getAllRecipes()) {
            model.addElement(recipe.getName());
        }
        recipeList.setModel(model);
    }

    public void selectRecipe(int recipeID) {
        Recipe recipe = recipeBook.getRecipeById(recipeID);
        if (recipe != null) {
            StringBuilder details = new StringBuilder();
            details.append("ID: ").append(recipe.getRecipeId()).append("\n");
            details.append("Name: ").append(recipe.getName()).append("\n");
            details.append("Ingredients: ").append(String.join(", ", recipe.getIngredients())).append("\n");
            details.append("Instructions: ").append(recipe.getInstructions());

            // Replace with ViewGUI later
            JOptionPane.showMessageDialog(this, details.toString(), "Recipe Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Recipe not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}