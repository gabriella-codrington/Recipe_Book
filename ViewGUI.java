import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.*;

public class ViewGUI extends JFrame{
    private Recipe selectedRecipe;
    private JLabel nameLabel;
    private JTextArea ingredientsArea;
    private JTextArea instructionsArea;
    private JLabel timeLabel;
    private JLabel dietTypeLabel;
    private JTextField ratingField;
    private JButton rateButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton cancelButton;

    private RecipeBook recipeBook;

    public ViewGUI(RecipeBook recipeBook, Recipe selectedRecipe) {
        this.recipeBook = recipeBook;
        this.selectedRecipe = selectedRecipe;

        setTitle("View Recipe");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Recipe Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(6, 1));
        nameLabel = new JLabel("Name: " + selectedRecipe.getName());
        timeLabel = new JLabel("Time: " + selectedRecipe.getTime() + " min");
        dietTypeLabel = new JLabel("Diet Type: " + selectedRecipe.getDietType());

        ingredientsArea = new JTextArea(String.join(", ", selectedRecipe.getIngredients()));
        ingredientsArea.setEditable(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);

        instructionsArea = new JTextArea(selectedRecipe.getInstructions());
        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        infoPanel.add(nameLabel);
        infoPanel.add(timeLabel);
        infoPanel.add(dietTypeLabel);
        infoPanel.add(new JLabel("Ingredients:"));
        infoPanel.add(new JScrollPane(ingredientsArea));
        infoPanel.add(new JLabel("Instructions:"));
        infoPanel.add(new JScrollPane(instructionsArea));

        // Rating Panel
        JPanel ratingPanel = new JPanel();
        ratingField = new JTextField(5);
        rateButton = new JButton("Rate");
        rateButton.addActionListener(this::rateRecipe);
        ratingPanel.add(new JLabel("Rating (1-5):"));
        ratingPanel.add(ratingField);
        ratingPanel.add(rateButton);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Close");

        modifyButton.addActionListener(e -> modifyRecipe());
        deleteButton.addActionListener(e -> deleteRecipe());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        // Add to Frame
        add(infoPanel, BorderLayout.CENTER);
        add(ratingPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void rateRecipe(ActionEvent e) {
        try {
            int rating = Integer.parseInt(ratingField.getText().trim());
            if (rating < 1 || rating > 5) throw new NumberFormatException();
            selectedRecipe.setRating(rating);
            JOptionPane.showMessageDialog(this, "Rating updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a number between 1 and 5.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyRecipe() {
        String newName = JOptionPane.showInputDialog(this, "Enter new name:", selectedRecipe.getName());
        String newIngredients = JOptionPane.showInputDialog(this, "Enter ingredients (comma-separated):", String.join(", ", selectedRecipe.getIngredients()));
        String newInstructions = JOptionPane.showInputDialog(this, "Enter instructions:", selectedRecipe.getInstructions());
        String newDietType = JOptionPane.showInputDialog(this, "Enter diet type:", selectedRecipe.getDietType());

        if (newName != null && newIngredients != null && newInstructions != null && newDietType != null) {
            selectedRecipe.setName(newName);
            selectedRecipe.setIngredients(Arrays.asList(newIngredients.split("\\s*,\\s*")));
            selectedRecipe.setInstructions(newInstructions);
            selectedRecipe.setDietType(newDietType);
            JOptionPane.showMessageDialog(this, "Recipe updated!");
            dispose();
            new ViewGUI(recipeBook, selectedRecipe);
        }        
    }

    private void deleteRecipe() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this recipe?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            recipeBook.deleteRecipe(selectedRecipe.getRecipeId());
            recipeBook.saveToFile("dataFile.txt");
            JOptionPane.showMessageDialog(this, "Recipe deleted.");
            dispose();
        }
    }
}
