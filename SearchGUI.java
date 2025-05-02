import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;

public class SearchGUI extends JFrame {
    private JTextField searchIngredients;
    private JButton searchButton;
    private JButton cancelButton;
    private JList<String> resultList;
    private JScrollPane resultScrollPane;
    
    private RecipeBook recipeBook;

    public SearchGUI(RecipeBook recipeBook) {
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

        add(resultScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchButton = new JButton("Search");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchRecipe(e));
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
                .anyMatch(ing -> ing.equalsIgnoreCase(trimmed)))
            .toList(); // Java 16+; use .collect(Collectors.toList()) if you're using Java 8
    }

    // Extract names for the list
    List<String> recipeNames = filteredRecipes.stream()
        .map(Recipe::getName)
        .toList();

    resultList.setListData(recipeNames.toArray(new String[0]));
}

    
}