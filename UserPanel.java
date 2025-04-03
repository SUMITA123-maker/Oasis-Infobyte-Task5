import javax.swing.*;

public class UserPanel extends JFrame {
    int userId;

    public UserPanel(int userId) {
        this.userId = userId;
        setTitle("User Panel");
        setSize(300, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton viewBooks = new JButton("View Books");
        viewBooks.setBounds(80, 20, 120, 30);
        add(viewBooks);

        JButton issueBook = new JButton("Issue Book");
        issueBook.setBounds(80, 60, 120, 30);
        add(issueBook);

        JButton returnBook = new JButton("Return Book");
        returnBook.setBounds(80, 100, 120, 30);
        add(returnBook);

        JButton advanceBook = new JButton("Advance Booking");
        advanceBook.setBounds(80, 140, 120, 30);
        add(advanceBook);

        viewBooks.addActionListener(e -> new ViewBooks());
        issueBook.addActionListener(e -> new IssueBook(userId));
        returnBook.addActionListener(e -> new ReturnBook(userId));
        advanceBook.addActionListener(e -> new AdvanceBooking(userId));

        setVisible(true);
    }
}
