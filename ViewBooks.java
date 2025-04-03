import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewBooks extends JFrame {
    public ViewBooks() {
        setTitle("Books List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window

        // Create text area for book list
        JTextArea textArea = new JTextArea(10, 50);
        textArea.setEditable(false);
        textArea.setText(
            "ID: 1 | Title: The Great Gatsby | Author: F. Scott Fitzgerald | Available: 5\n" +
            "ID: 2 | Title: Introduction to Algorithms | Author: Thomas H. Cormen | Available: 3\n" +
            "ID: 3 | Title: Clean Code | Author: Robert C. Martin | Available: 4\n" +
            "ID: 4 | Title: Data Structures | Author: Seymour Lipschutz | Available: 2\n"
        );

        JScrollPane scrollPane = new JScrollPane(textArea);

        // Create Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
            }
        });

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ViewBooks();
    }
}




