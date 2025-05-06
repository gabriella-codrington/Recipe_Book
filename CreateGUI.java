import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;


public class CreateGUI extends JFrame{
    private JTextField dishNameField;
    private JTextArea ingredientsField;
    private JTextArea instructionsField;
    private JTextField timeField;
    private JTextField dietTypeField;
    private JButton submitButton;
    private JButton cancelButton;
    private RecipeBook recipeBook;

    public CreateGUI(RecipeBook recipeBook){
        this.recipeBook = recipeBook;

        setTitle("Create a New Recipe");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //the panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6,2,10,10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));

        //Name
        formPanel.add(new JLabel("Dish Name:"));
        dishNameField = new JTextField();
        formPanel.add(dishNameField);

        //Diet type
        formPanel.add(new JLabel("Diet Type:"));
        dietTypeField = new JTextField();
        formPanel.add(dietTypeField);

        //Ingredients
        formPanel.add(new JLabel("Ingredients (line-seperated):"));
        ingredientsField = new JTextArea(6, 20);
        ingredientsField.setLineWrap(true);
        ingredientsField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane (ingredientsField));

        //Instructions
        formPanel.add(new JLabel("Instructions:"));
        instructionsField = new JTextArea(6, 20);
        instructionsField.setLineWrap(true);
        instructionsField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(instructionsField));

        //Time required
        formPanel.add(new JLabel("Time:"));
        timeField = new JTextField();
        formPanel.add(timeField);


        add(formPanel, BorderLayout.CENTER);

        //the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        //action listeners for the buttons
        submitButton.addActionListener(this::handleSubmit);
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    //submit handler
    private void handleSubmit(ActionEvent e){
        try{
            String name = dishNameField.getText().trim();
            List<String> ingredients = Arrays.asList(ingredientsField.getText().trim().split("\\r?\\n"));
            String instructions = instructionsField.getText().trim();
            String time = timeField.getText().trim();
            String dietType = dietTypeField.getText().trim();

            if(name.isEmpty() || ingredients.isEmpty() || instructions.isEmpty() || time.isEmpty() || dietType.isEmpty()){
                JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Recipe newRecipe = new Recipe(name, ingredients, instructions,time, dietType);
            recipeBook.addRecipe(newRecipe);
            recipeBook.saveToFile("dataFile.txt");
            JOptionPane.showMessageDialog(this, "Recipe was created successfully");
            dispose();
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this,"Error: " + ex.getMessage(), "Create Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
