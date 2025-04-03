import javax.swing.*;
import java.sql.*;

public class LoginForm extends JFrame {

    public LoginForm() {
        setTitle("Library Login");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 30, 80, 25);
        add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(100, 30, 150, 25);
        add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 70, 80, 25);
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(100, 70, 150, 25);
        add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 110, 100, 30);
        add(loginBtn);

        loginBtn.addActionListener(e -> {
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
                stmt.setString(1, userField.getText());
                stmt.setString(2, new String(passField.getPassword()));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String role = rs.getString("role");
                    int userId = rs.getInt("id");
                    if (role.equals("admin")) {
                        new AdminPanel();
                    } else {
                        new UserPanel(userId);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}

