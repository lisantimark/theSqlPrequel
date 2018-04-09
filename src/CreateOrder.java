import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by owmer on 4/9/2018.
 */

public class CreateOrder extends JFrame{

    public static void main(String[] args) throws SQLException {
        new CreateOrder();
    }

    Connection c = Login.SQLiteJDBC();
    Statement s = c.createStatement();
    ResultSet rs = s.executeQuery("select * from products");
    JButton addItem = new JButton("Add Item");
    JButton sendOrder = new JButton("Send Order");
    JPanel panel = new JPanel();
    JTable productsTable = new JTable(Login.buildTableModel(rs));
    String[] colHeadings = {"p_id", "format", "name", "category", "d_id", "version", "price", "rating", "released", "qoh"};
    DefaultTableModel model = new DefaultTableModel(30, colHeadings.length) ;
    JTable currentOrder = new JTable(model);
    JScrollPane currentTable =  new JScrollPane(productsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JScrollPane orderTable =  new JScrollPane(currentOrder, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    String[] menuOptions = { "All Products", "Developer", "Format", "Top Rating", "Price Low to High", "Price High to Low" };
    JComboBox searchMenu = new JComboBox(menuOptions);

    CreateOrder() throws SQLException {
        super("Create Order");
        setSize(900, 900);
        setLocation(0, 0);
        panel.setLayout(null);
        searchMenu.setBounds(70, 15, 150, 25);
        currentTable.setBounds(50, 50, 800, 250);
        orderTable.setBounds(50, 400, 800, 250);
        addItem.setBounds(350, 320, 200, 50);
        sendOrder.setBounds(350, 700, 200, 50);
        panel.add(searchMenu);
        panel.add(currentTable);
        panel.add(addItem);
        panel.add(orderTable);
        panel.add(sendOrder);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionlogin() {
        addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

            }
        });
    }



}

