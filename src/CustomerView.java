/**
 * Created by owmer on 3/24/2018.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CustomerView extends JFrame {

    public static void main(String[] args) throws SQLException {
        new CustomerView();
    }

    Connection c = Login.SQLiteJDBC();
    Statement s = c.createStatement();
    ResultSet rs = s.executeQuery("select * from products");
    JLabel welcome = new JLabel("WOO CUSTOMER VIEW");
    JPanel panel = new JPanel();
    JTable table = new JTable(Login.buildTableModel(rs));
    JScrollPane currentTable =  new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    CustomerView() throws SQLException {
        super("Customer View");
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
