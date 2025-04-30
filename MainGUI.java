import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame{
    
    private JButton createButton;
    private JButton searchButton;
    private JButton browseButton;

    public MainGUI(){
        //set up frame
        setTitle("Recipe Book - Main Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 40, 60));

        //create buttons
        createButton = new JButton("Create New Recipe");
        searchButton = new JButton("Search Recipes");
        browseButton = new JButton("Browse Recipes");

        //make the button size better
        Dimension buttonSize = new Dimension(200, 40);
        createButton.setPreferredSize(buttonSize);
        searchButton.setPreferredSize(buttonSize);
        browseButton.setPreferredSize(buttonSize);

    
        //adding action listeners to out buttons
        createButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new CreateGUI();
            }
        });

        searchButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new SearcheGUI();
            }
        });

        browseButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new BrowseGUI();
            }
        });

        //layout
        // setLayout(new GridLayout(3, 1, 10, 10));
        add(createButton);
        add(searchButton);
        add(browseButton);

        setVisible(true);

    }


    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new MainGUI());
    }

}

