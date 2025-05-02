import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BrowseGUI extends JFrame {
    private JList<String> recipeList;
    private JScrollPane browseScrollPane;
    private JButton viewButton;
    private JButton cancelButton;
    private List<Recipe> allRecipes;

    public BrowseGUI(List<Recipe> recipes) {
        this.allRecipes = recipes;
        setTitle("Browse Recipes");
        setSize(400, 300);
        setLayout(new BorderLayout());

        recipeList = new JList<>();
        browseScrollPane = new JScrollPane(recipeList);
        viewButton = new JButton("View");
        cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewButton);
        buttonPanel.add(cancelButton);

        add(browseScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        displayRecipes();

        viewButton.addActionListener(e -> {
            int index = recipeList.getSelectedIndex();
            if (index != -1) {
                selectRecipe(index);
            }
        });

        cancelButton.addActionListener(e -> cancel());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void displayRecipes() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Recipe r : allRecipes) {
            model.addElement(r.getName());
        }
        recipeList.setModel(model);
    }

    public void selectRecipe(int index) {
        Recipe selected = allRecipes.get(index);
        JOptionPane.showMessageDialog(this,
                "Name: " + selected.getName() + "\nIngredients: " + selected.getIngredients(),
                "Recipe Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public void cancel() {
        dispose();
    }
}
