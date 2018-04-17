/**
 * Created by owmer on 3/24/2018.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class CustomerView extends JFrame {


    public static void main(String[] args) throws SQLException { //Customer Class which will have customer views/ functions for ordering a product
        new CustomerView();
    }


    static Connection c = Login.JDBC();
    int c_id = 100001;
    Statement s = c.createStatement();
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
                PreparedStatement updatePass = null;  //Prepared Statement for adding to customer table
                try {
                    updatePass = c.prepareStatement("update customers set password = ? where c_id = ?");
                    updatePass.setString(1,password);
                    updatePass.setInt(2, 100001);
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
                    dispose();
                } catch (SQLException se) {
                }
            }
        });
        orderHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    PreparedStatement buildHistory = c.prepareStatement("{call compileHistory(?)}");
                    buildHistory.setInt(1,100001);
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


}