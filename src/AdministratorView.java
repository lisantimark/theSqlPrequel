/**
 * Created by owmer on 3/25/2018.
 */
import javax.swing.*;
import java.sql.*;

public class AdministratorView extends JFrame {

    public static void main(String[] args) throws SQLException {
        new AdministratorView();
    }

    Connection c = Login.SQLiteJDBC();
    Statement s = c.createStatement();
    ResultSet rs = s.executeQuery("select * from customers_cv");
    JLabel welcome = new JLabel("Testing");
    JPanel panel = new JPanel();
    JTable table = new JTable(Login.buildTableModel(rs));
    JScrollPane currentTable =  new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    AdministratorView() throws SQLException {
        super("Admin View");
        setSize(900,900);
        setLocation(0,0);
        panel.setLayout(null);
        welcome.setBounds(70,50,150,60);
        currentTable.setBounds(100, 600, 700, 250);
        panel.add(welcome);
        panel.add(currentTable);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}