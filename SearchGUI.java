import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class SearchGUI extends JFrame {
    private JTextField searchIngredients;
    private JButton searchButton;
    private JButton cancelButton;
    private JList<String> resultList;
    private JScrollPane resultScrollPane;
    private List<Recipe> allRecipes;

    public SearchGUI(List<Recipe> recipes) {
        this.allRecipes = recipes;
        setTitle("Search Recipes");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        searchIngredients = new JTextField(20);
        searchButton = new JButton("Search");
        cancelButton = new JButton("Cancel");

        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchIngredients);
        topPanel.add(searchButton);
        topPanel.add(cancelButton);

        resultList = new JList<>();
        resultScrollPane = new JScrollPane(resultList);

        add(topPanel, BorderLayout.NORTH);
        add(resultScrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String query = searchIngredients.getText().toLowerCase();
            List<Recipe> results = searchRecipe(query);
            DefaultListModel<String> model = new DefaultListModel<>();
            for (Recipe r : results) {
                model.addElement(r.getName());
            }
            resultList.setModel(model);
        });

        cancelButton.addActionListener(e -> cancel());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public List<Recipe> searchRecipe(String query) {
        return allRecipes.stream()
                .filter(r -> r.getName().toLowerCase().contains(query) ||
                             r.getIngredients().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    public void cancel() {
        dispose();
    }
}
