import java.io.*;
import java.util.*;
import javax.swing.*;

public class SearchGUI extends JFrame {
    private JTextField searchIngredients;
    private JButton searchButton;
    private JButton cancelButton;
    private JList<String> resultList;
    private JScrollPane resultScrollPane;

    public SearchGUI() {
        resultList = new JList<>();
        resultScrollPane = new JScrollPane(resultList); 
    
        add(searchIngredients);
        add(searchButton);
        add(cancelButton);
        add(resultScrollPane); 

        cancelButton.addActionListener(e -> cancel());
    }
    

    public List<String> searchRecipe(String ingredientInput) {
        List<String> matchedRecipes = new ArrayList<>();
        String[] ingredientsToSearch = ingredientInput.split(",");

        try (BufferedReader reader = new BufferedReader(new FileReader("dataFile.txt"))) {
            String line;
            String currentName = null;
            String currentIngredients = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name:")) {
                    currentName = line.substring(5).trim();
                } else if (line.startsWith("Ingredients:")) {
                    currentIngredients = line.substring(12).toLowerCase();

                    boolean matches = Arrays.stream(ingredientsToSearch)
                        .map(String::trim)
                        .allMatch(currentIngredients::contains);

                    if (matches && currentName != null) {
                        matchedRecipes.add(currentName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matchedRecipes;
    }

    public void cancel() {
        searchIngredients.setText("");
        resultList.setListData(new String[0]);
    }
}