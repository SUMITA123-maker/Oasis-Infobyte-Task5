import javax.swing.*;
import java.sql.*;

public class AddBookForm extends JFrame {
    public AddBookForm() {
        setTitle("Add Book");
        setSize(300, 200);
        setLayout(null);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(20, 20, 80, 25);
        add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBounds(100, 20, 150, 25);
        add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(20, 60, 80, 25);
        add(authorLabel);

        JTextField authorField = new JTextField();
        authorField.setBounds(100, 60, 150, 25);
        add(authorField);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(100, 100, 80, 30);
        add(addBtn);

        addBtn.addActionListener(e -> {
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (title, author) VALUES (?,?)");
                stmt.setString(1, titleField.getText());
                stmt.setString(2, authorField.getText());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book Added Successfully!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        setVisible(true);
    }
}

