import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdvanceBooking extends JFrame {
    private int userId;
    private JTextField bookIdField;
    private JButton bookButton, backButton;

    public AdvanceBooking(int userId) {
        this.userId = userId;

        setTitle("Advance Booking");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new FlowLayout());

        JLabel bookIdLabel = new JLabel("Enter Book ID:");
        bookIdField = new JTextField(10);
        bookButton = new JButton("Book");

        bookButton.addActionListener(e -> bookAdvance());

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
        add(bookButton);
        add(backButton);

        setVisible(true);
    }

    private void bookAdvance() {
        try (Connection conn = DBConnection.getConnection()) {
            int bookId = Integer.parseInt(bookIdField.getText());

            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT available FROM books WHERE id=?"
            );
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("available") == 0) {
                PreparedStatement bookStmt = conn.prepareStatement(
                    "INSERT INTO advance_booking (user_id, book_id, booking_date) VALUES (?, ?, CURDATE())"
                );
                bookStmt.setInt(1, userId);
                bookStmt.setInt(2, bookId);
                bookStmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book Reserved Successfully!");
                dispose();
                new UserPanel(userId);
            } else {
                JOptionPane.showMessageDialog(this, "Book is available, no need for advance booking!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}


