import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by owmer on 4/9/2018.
 */

public class CreateOrder extends JFrame{  //Separate Frame to Visually show whats being ordered and start and complete a transaction on the back end

    public static void main(String[] args) throws SQLException {
        new CreateOrder();
        try {
            c.setAutoCommit(false);
            CallableStatement cStmt = c.prepareCall("{call newOrder(?,?,?)}");
            cStmt.setBigDecimal(1, BigDecimal.valueOf(7788));
            cStmt.setBigDecimal(2, BigDecimal.valueOf(100003));
            cStmt.setString(3, "2016-05-02");
            cStmt.execute();
            cStmt.close();
        } catch(SQLException se){
            c.rollback();
            System.out.println("Transaction incomplete, rolled back");
            }
        }

    static Connection c = Login.JDBC();
    Statement s = c.createStatement();
    ResultSet rs = s.executeQuery("select * from products");
    JButton addItem = new JButton("Add Item");
    JButton sendOrder = new JButton("Send Order");
    JPanel panel = new JPanel();
    JTable productsTable = new JTable(Login.buildTableModel(rs));
    String[] colHeadings = {"p_id", "format", "name", "category", "d_id", "version", "price", "rating", "released", "qoh"};
    DefaultTableModel model = new DefaultTableModel(30, colHeadings.length) ;
    JTable currentOrder = new JTable(model);
    JScrollPane currentTable =  new JScrollPane(productsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); //Table, subject to change due to drop down queries
    JScrollPane orderTable =  new JScrollPane(currentOrder, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    String[] menuOptions = { "All Products", "Developer", "Format", "Top Rating", "Price Low to High", "Price High to Low" }; //Will be used to call function with parameter to run query to be used
    JComboBox searchMenu = new JComboBox(menuOptions);

    CreateOrder() throws SQLException { //Swing stuff
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
        actionOrder();
    }

    public void actionOrder() {  //This will initiate procedure / transaction and also change visual elements
        addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    CallableStatement oStmt = c.prepareCall("{call updateTrans(?,?,?,?)}");
                    oStmt.setBigDecimal(1, BigDecimal.valueOf(7788));
                    oStmt.setString(2, "3970755369");
                    oStmt.setString(3,"Physical");
                    oStmt.setBigDecimal(4, BigDecimal.valueOf(3));
                    oStmt.execute();
                    oStmt.close();
                }
                catch(SQLException se){
                    // If there is any error.
                    try {
                        c.rollback();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Transaction incomplete, rolled back");
                    }
                }
            }
        });
        sendOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    c.commit();
                    System.out.println("Transaction Complete");
                }
                catch(SQLException se){
                    // If there is any error.
                    try {
                        c.rollback();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Transaction incomplete, rolled back");
                }
                try {
                    c.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}

