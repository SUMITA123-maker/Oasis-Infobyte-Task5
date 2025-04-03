import javax.swing.*;

public class AdminPanel extends JFrame {
    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton addBook = new JButton("Add Book");
        addBook.setBounds(80, 20, 120, 30);
        add(addBook);

        JButton viewBooks = new JButton("View Books");
        viewBooks.setBounds(80, 60, 120, 30);
        add(viewBooks);

        JButton report = new JButton("Reports");
        report.setBounds(80, 100, 120, 30);
        add(report);

        addBook.addActionListener(e -> new AddBookForm());
        viewBooks.addActionListener(e -> new ViewBooks());
        report.addActionListener(e -> new ReportGenerator());

        setVisible(true);
    }
}


