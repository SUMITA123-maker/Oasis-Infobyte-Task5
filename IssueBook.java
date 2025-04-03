import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class IssueBook extends JFrame {
    private int userId;
    private JTextField bookIdField;
    private JButton issueButton, backButton;

    public IssueBook(int userId) {
        this.userId = userId;

        setTitle("Issue Book");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new FlowLayout());

        JLabel bookIdLabel = new JLabel("Enter Book ID:");
        bookIdField = new JTextField(10);
        issueButton = new JButton("Issue Book");

        issueButton.addActionListener(e -> issueBook());

        // Back Button
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
                new UserPanel(userId); // Open User Panel
            }
        });

        add(bookIdLabel);
        add(bookIdField);
        add(issueButton);
        add(backButton);

        setVisible(true);
    }

    private void issueBook() {
        try (Connection conn = DBConnection.getConnection()) {
            int bookId = Integer.parseInt(bookIdField.getText());

            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT available FROM books WHERE id=?"
            );
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("available") > 0) {
                PreparedStatement issueStmt = conn.prepareStatement(
                    "INSERT INTO issued_books (user_id, book_id, issue_date) VALUES (?, ?, CURDATE())"
                );
                issueStmt.setInt(1, userId);
                issueStmt.setInt(2, bookId);
                issueStmt.executeUpdate();

                PreparedStatement updateBook = conn.prepareStatement(
                    "UPDATE books SET available=0 WHERE id=?"
                );
                updateBook.setInt(1, bookId);
                updateBook.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book Issued Successfully!");
                dispose();
                new UserPanel(userId);
            } else {
                JOptionPane.showMessageDialog(this, "Book not available!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}


