import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by owmer on 4/9/2018.
 */

public class CreateOrder extends JFrame{
    //Ordering menu, visually shows what products
                                        //Begins transaction inserts values to orders, then odetails
                                        //Transaction is rolled back until 'send order' is clicked

     public static void main(String[] args) throws SQLException {
         new CreateOrder();
         try {
             c.setAutoCommit(false);  //Initiates Transaction
             //get customer id
             CallableStatement cStmt = c.prepareCall("{call newOrder(?,?)}"); //This statement creates a new insert to order table upon class starting
              //This is initiated at class running but not committed unless button send order pushed
             cStmt.setBigDecimal(1, BigDecimal.valueOf(100003)); //o_id needs to be created here before any odetails with same id can be created
             cStmt.setString(2, "2016-05-02");
             cStmt.execute();

         } catch (SQLException se) {
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
    String[] colHeadings =  {"p_id", "format", "name", "category", "d_id", "version", "price", "rating", "released", "qty"};
    DefaultTableModel model = new DefaultTableModel(colHeadings, 0);
    JTable currentOrder = new JTable(model); //Creates empty table to visually see what you're ordering
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

    public void actionOrder() {
        addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String qty = (String) JOptionPane.showInputDialog(panel, "How many?", "Enter Quantity", JOptionPane.PLAIN_MESSAGE, null, null, 1);
                try {
                    TableModel model1 = productsTable.getModel(); //Adds line from jTable1 to jTable2 as you add products
                    int[] indexs = productsTable.getSelectedRows();
                    Object[] row = new Object[10];
                    DefaultTableModel model2 = (DefaultTableModel) currentOrder.getModel();
                    for(int i = 0; i < indexs.length; i++) {  //Loop that gets values at each index, and adds them to second table
                        row[0] =model1.getValueAt(indexs[i],0);
                        row[1] =model1.getValueAt(indexs[i],1);
                        row[2] =model1.getValueAt(indexs[i],2);
                        row[3] =model1.getValueAt(indexs[i],3);
                        row[4] =model1.getValueAt(indexs[i],4);
                        row[5] =model1.getValueAt(indexs[i],5);
                        row[6] =model1.getValueAt(indexs[i],6);
                        row[7] =model1.getValueAt(indexs[i],7);
                        row[8] =model1.getValueAt(indexs[i],8);
                        row[9] = qty;
                        model2.addRow(row);
                    }
                    //Order ID

                    //Product ID and Format
                    CallableStatement oStmt = c.prepareCall("{call updateTrans(?,?,?)}"); //This calls a stored procedure that inserts into odetails
                    oStmt.setString(1, (String) row[0]);
                    oStmt.setString(2, (String) row[3]);
                    oStmt.setBigDecimal(3, BigDecimal.valueOf(Long.parseLong(qty))); //This qty is determined by popup box
                    oStmt.execute();
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
                    c.setAutoCommit(true);
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
            }
        });
    }
}

