/**
 * Created by owmer on 3/24/2018.
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;


public class CustomerView extends JFrame {




    public static void main(String[] args) throws SQLException { //Customer Class which will have customer views/ functions for ordering a product
        new CustomerView();
    }

    Connection c = Login.JDBC();
    String customer_id = Login.puname;
    Statement s = c.createStatement();
    ResultSet id = s.executeQuery("select c_id from customers where email ='"+ customer_id +"'");
    int c_id = 0;
    Statement s2 = c.createStatement();
    ResultSet rs2 = s2.executeQuery("select * from admin_cv");
    JButton refresh = new JButton("Refresh");
    JButton orderHistory = new JButton("View Order History");
    JButton beginOrder = new JButton("Start Order");
    JButton changePass = new JButton("Change Password");
    JLabel AccountInfo = new JLabel("Account Info");
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Top 10 products sold");
    JTable buildView = new JTable(Login.buildTableModel(rs2));
    JScrollPane viewTable = new JScrollPane(buildView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    ResultSet rs1 = s2.executeQuery("select products.pname Product_name, category, price from products, odetails \n" +
            "where products.p_id=odetails.p_id \n" +
            "group by pname \n" +
            "order by sum(qty) \n" +
            "desc limit 10;");
    JTable topSellers = new JTable(Login.buildTableModel(rs1));
    JScrollPane topSellersTable =  new JScrollPane(topSellers, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


    CustomerView() throws SQLException {
        super("Customer View");
        setSize(900, 900);
        setLocation(0, 0);
        panel.setLayout(null);
        if (id.next()) {
            c_id = id.getInt(1);
            System.out.println(c_id);
        }
        PreparedStatement acc = c.prepareStatement("select email, c_card, address, zipcode from customers where c_id = ?");
        acc.setInt(1, c_id);
        ResultSet rs3 = acc.executeQuery();
        JTable buildAccount = new JTable(Login.buildTableModel(rs3));
        JScrollPane accountInfo = new JScrollPane(buildAccount, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        changePass.setBounds(515, 225, 150, 25);
        viewTable.setBounds(350, 50, 500, 100);
        AccountInfo.setBounds(550, 150, 500, 100);
        accountInfo.setBounds(350, 275, 500, 50);
        orderHistory.setBounds(70, 400, 150, 25);
        topSellersTable.setBounds(75, 600, 400, 200);
        beginOrder.setBounds(70, 100, 200,200);
        label.setBounds(110, 525, 200, 100);
        refresh.setBounds(15, 15, 100, 25);
        panel.add(viewTable);
        panel.add(orderHistory);
        panel.add(AccountInfo);
        panel.add(accountInfo);
        panel.add(changePass);
        panel.add(topSellersTable);
        panel.add(beginOrder);
        panel.add(label);
        panel.add(refresh);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        actionlogin();
        setVisible(true);
    }


    public void actionlogin() {
        changePass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String password = (String) JOptionPane.showInputDialog(panel, "Enter New Password", "Update Password", JOptionPane.PLAIN_MESSAGE, null, null, null);
                PreparedStatement updatePass;                //Prepared Statement for adding to customer table
                try {
                    updatePass = c.prepareStatement("update customers set password = ? where c_id = ?");
                    updatePass.setString(1,password);
                    updatePass.setInt(2, c_id);
                    updatePass.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                }
           });
        beginOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                    try {
                        new CreateOrder();
                                                                                    //Initiates Transaction
                                                                                    //c.setAutoCommit(false);
                                                                                    //get customer id
                        CallableStatement cStmt = c.prepareCall("{call newOrder(?,?)}"); //This statement creates a new insert to order table upon class starting
                                                                                    //This is initiated at class running but not committed unless button send order pushed
                        cStmt.setInt(1, c_id);                        //o_id needs to be created here before any odetails with same id can be created
                        cStmt.setString(2, "2018-04-25");
                        cStmt.execute();
                    } catch (SQLException se) {
                        try {
                            c.rollback();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Transaction incomplete, rolled back");
                    }

            }
        });
        orderHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    PreparedStatement buildHistory = c.prepareStatement("{call compileHistory(?)}");
                    buildHistory.setInt(1,c_id);
                    buildHistory.execute();
                    ResultSet rs5 = buildHistory.getResultSet();
                    JTable history = new JTable(Login.buildTableModel(rs5));
                    JScrollPane historyTable =  new JScrollPane(history, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    historyTable.setBounds(350, 365, 500, 100);
                    panel.add(historyTable);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        refresh.addActionListener(new ActionListener() { //button uses selected cell to delete from SQL table at that selected value
            public void actionPerformed(ActionEvent ae) {
                dispose();
                try {
                    new AdministratorView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
   }

    public class CreateOrder extends JFrame{
                                                        //Ordering menu, visually shows what products
                                                        //Begins transaction inserts values to orders, then odetails
                                                        // Transaction is rolled back until 'send order' is clicked
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
            c.setAutoCommit(false);                                                                         //MOVED SET AUTOCOMMIT HERE
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
                        //Order ID created with AUTO_INCREMENT

                        //Product ID and Format
                        Statement newID = c.createStatement(); //prepared statement to iterate customer id's
                        ResultSet rs = newID.executeQuery("select max(o_id) from orders");
                        while(rs.next()){
                            int id = rs.getInt(1);
                            System.out.println(id);
                            CallableStatement oStmt = c.prepareCall("{call updateTrans(?,?,?,?)}"); //This calls a stored procedure that inserts into odetails
                            oStmt.setInt(1, id);
                            oStmt.setString(2, (String) row[0]);
                            oStmt.setString(3, (String) row[3]);
                            oStmt.setBigDecimal(4, BigDecimal.valueOf(Long.parseLong(qty))); //This qty is determined by popup box
                            oStmt.execute();
                        }
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
                        c.setAutoCommit(true);
                        dispose();
                    }
                    catch(SQLException se){
                        // If there is any error.

                        System.out.println("Transaction incomplete, rolled back");
                    }
                }
            });
        }
    }




}