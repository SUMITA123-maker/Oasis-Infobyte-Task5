import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReturnBook extends JFrame {
    private int userId;
    private JTextField bookIdField;
    private JButton returnButton, backButton;

    public ReturnBook(int userId) {
        this.userId = userId;

        setTitle("Return Book");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new FlowLayout());

        JLabel bookIdLabel = new JLabel("Enter Book ID:");
        bookIdField = new JTextField(10);
        returnButton = new JButton("Return Book");

        returnButton.addActionListener(e -> returnBook());

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
        add(returnButton);
        add(backButton);

        setVisible(true);
    }

    private void returnBook() {
        try (Connection conn = DBConnection.getConnection()) {
            int bookId = Integer.parseInt(bookIdField.getText());

            PreparedStatement issuedStmt = conn.prepareStatement(
                "SELECT issue_date FROM issued_books WHERE user_id=? AND book_id=?"
            );
            issuedStmt.setInt(1, userId);
            issuedStmt.setInt(2, bookId);
            ResultSet rs = issuedStmt.executeQuery();

            if (rs.next()) {
                LocalDate issueDate = rs.getDate("issue_date").toLocalDate();
                LocalDate today = LocalDate.now();
                long days = ChronoUnit.DAYS.between(issueDate, today);
                long fine = (days > 7) ? (days - 7) * 5 : 0;

                PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE issued_books SET return_date=? WHERE user_id=? AND book_id=?"
                );
                stmt.setDate(1, Date.valueOf(today));
                stmt.setInt(2, userId);
                stmt.setInt(3, bookId);
                stmt.executeUpdate();

                PreparedStatement updateBook = conn.prepareStatement(
                    "UPDATE books SET available=1 WHERE id=?"
                );
                updateBook.setInt(1, bookId);
                updateBook.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book Returned! Fine: ₹" + fine);
                dispose();
                new UserPanel(userId);
            } else {
                JOptionPane.showMessageDialog(this, "No such issued book found!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
