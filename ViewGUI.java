import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;



public class ViewGUI extends JFrame{
    private Recipe selectedRecipe;
    private JTextArea ingredientsArea;
    private JTextArea instructionsArea;
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

        //main panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        // Recipe Info Panel
        contentPanel.add(new JLabel("Name: " + selectedRecipe.getName()));
        contentPanel.add((Box.createVerticalStrut(10)));
        contentPanel.add(new JLabel("Time: " + selectedRecipe.getTime() + " min"));
        contentPanel.add((Box.createVerticalStrut(10)));
        contentPanel.add(new JLabel("Diet Type: " + selectedRecipe.getDietType()));
        contentPanel.add((Box.createVerticalStrut(20)));
        contentPanel.add(new JLabel("Rating: " + selectedRecipe.getRating()));
        contentPanel.add((Box.createVerticalStrut(20)));

        //ingredient
        contentPanel.add(new JLabel("Ingredients:"));
        ingredientsArea = new JTextArea(String.join(", ", selectedRecipe.getIngredients()), 5, 40);
        ingredientsArea.setEditable(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);
        JScrollPane ingredientsScroll = new JScrollPane(ingredientsArea);
        ingredientsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ingredientsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ingredientsScroll.setPreferredSize(new Dimension(400, 80));
        contentPanel.add(ingredientsScroll);
        contentPanel.add(Box.createVerticalStrut(20));

        // Instructions
        contentPanel.add(new JLabel("Instructions:"));
        instructionsArea = new JTextArea(selectedRecipe.getInstructions(), 6,40);
        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        JScrollPane instructionsScroll = new JScrollPane(instructionsArea);
        instructionsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        instructionsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        instructionsScroll.setPreferredSize(new Dimension(400, 120));
        contentPanel.add(instructionsScroll);

        // Rating Panel
        JPanel ratingPanel = new JPanel();
        ratingField = new JTextField(5);
        rateButton = new JButton("Rate");
        rateButton.addActionListener(this::rateRecipe);
        ratingPanel.add(new JLabel("Rating (1-5):"));
        ratingPanel.add(ratingField);
        ratingPanel.add(rateButton);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
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
        add(contentPanel, BorderLayout.CENTER);
        add(ratingPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void rateRecipe(ActionEvent e) {
        try {
            double input = Double.parseDouble(ratingField.getText().trim());
            int rating = (int) input;
            if (input != rating ||rating < 1 || rating > 5) throw new NumberFormatException();
            selectedRecipe.setRating(rating);
            recipeBook.saveToFile("dataFile.txt");
            JOptionPane.showMessageDialog(this, "Rating updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a number between 1 and 5.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyRecipe() {
        JDialog dialog = new JDialog(this, "Modify Recipe", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JTextField nameField = new JTextField(selectedRecipe.getName());
        JTextField dietTypeField = new JTextField(selectedRecipe.getDietType());
        JTextArea ingredientsArea = new JTextArea(String.join(", ", selectedRecipe.getIngredients()), 5, 20);
        JTextArea instructionsArea = new JTextArea(selectedRecipe.getInstructions());

        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("DietType:"));
        formPanel.add(dietTypeField);
        formPanel.add(new JLabel("Ingredients: (comma-seperated):"));
        formPanel.add(new JScrollPane(ingredientsArea));
        formPanel.add(new JLabel("Instructions:"));
        formPanel.add(new JScrollPane(instructionsArea));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.addActionListener(e -> {
           selectedRecipe.setName(nameField.getText().trim());
           selectedRecipe.setDietType(dietTypeField.getText().trim());
           selectedRecipe.setIngredients(Arrays.asList(ingredientsArea.getText().trim().split("\\s*,\\s*")));
           selectedRecipe.setInstructions(instructionsArea.getText().trim());
           recipeBook.saveToFile("dataFile.txt");
           dialog.dispose();
           JOptionPane.showMessageDialog(this, "Recipe updated!");
           dispose(); //close current view
           new ViewGUI(recipeBook, selectedRecipe);
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
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
