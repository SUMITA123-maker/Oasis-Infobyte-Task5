import javax.swing.*;
import java.sql.*;

public class ReportGenerator extends JFrame {
    public ReportGenerator() {
        setTitle("Report Generator");
        setSize(400, 300);
        setLayout(null);

        JTextArea reportArea = new JTextArea();
        reportArea.setBounds(20, 20, 350, 220);
        add(reportArea);

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM issued_books WHERE return_date IS NULL");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reportArea.append("User ID: " + rs.getInt("user_id") + " | Book ID: " + rs.getInt("book_id") + " | Issue Date: " + rs.getDate("issue_date") + "\n");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        setVisible(true);
    }
}

