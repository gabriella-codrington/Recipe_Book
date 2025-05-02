import java.io.*;
import javax.swing.*;

public class BrowseGUI extends JFrame {
    private JList<String> recipeList;
    private JScrollPane browseScrollPane;
    private JButton viewButton;
    private JButton cancelButton;

    public BrowseGUI() {
        recipeList = new JList<>();
        browseScrollPane = new JScrollPane(recipeList);
    
        add(viewButton);
        add(cancelButton);
        add(browseScrollPane);

        cancelButton.addActionListener(e -> cancel());
    }

    public void displayRecipes() {
        DefaultListModel<String> model = new DefaultListModel<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dataFile.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name:")) {
                    model.addElement(line.substring(5).trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        recipeList.setModel(model);
    }

    public void selectRecipe(int recipeID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("dataFile.txt"))) {
            String line;
            boolean found = false;
            StringBuilder recipeDetails = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID:") && Integer.parseInt(line.substring(3).trim()) == recipeID) {
                    found = true;
                    recipeDetails.append(line).append("\n");
                } else if (found && !line.equals("---")) {
                    recipeDetails.append(line).append("\n");
                } else if (found && line.equals("---")) {
                    break;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(this, recipeDetails.toString(), "Recipe Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Recipe not found", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        recipeList.clearSelection();
    }
}