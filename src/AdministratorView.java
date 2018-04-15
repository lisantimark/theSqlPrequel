/**
 * Created by owmer on 3/25/2018.
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.Date;


public class AdministratorView extends JFrame {

    public static void main(String[] args) throws SQLException {
        new AdministratorView();
    }

    Connection c = Login.JDBC();
    BigDecimal o_id;
    Statement s = c.createStatement();
    Statement s1 = c.createStatement(); //need to reduce redundancy in creating statements
    Statement s2 = c.createStatement();
    Statement s3 = c.createStatement();
    ResultSet rs = s.executeQuery("select * from customers_cv");
    ResultSet rs2 = s1.executeQuery("select * from developers");
    ResultSet rs3 = s2.executeQuery("select * from products");
    ResultSet rs4 = s3.executeQuery("select * from orders");
    JButton deleteCustomer = new JButton("Delete Customer");
    JButton deleteDeveloper = new JButton("Delete Developer");
    JButton sendOrder = new JButton("Send Order");
    JButton viewOdetails = new JButton("View Details");
    JPanel panel = new JPanel();
    JTable buildView = new JTable(Login.buildTableModel(rs));
    JTable developerTAble = new JTable(Login.buildTableModel(rs2));
    JTable buildProducts = new JTable(Login.buildTableModel(rs3));
    JTable buildOrders = new JTable(Login.buildTableModel(rs4));
    JScrollPane viewTable =  new JScrollPane(buildView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); //Tables for 'admin' views
    JScrollPane devTable =  new JScrollPane(developerTAble, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JScrollPane productsTable =  new JScrollPane(buildProducts, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JScrollPane ordersTable =  new JScrollPane(buildOrders, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


    AdministratorView() throws SQLException {
        super("Admin View");
        setSize(900,900);
        setLocation(0,0);
        panel.setLayout(null);
        viewTable.setBounds(350, 50, 500, 100);
        devTable.setBounds(350, 155, 500, 100);
        ordersTable.setBounds(350, 260, 500, 100);
        productsTable.setBounds(100, 600, 700, 200);
        deleteCustomer.setBounds(100, 75, 200, 25);
        deleteDeveloper.setBounds(100, 180, 200, 25);
        sendOrder.setBounds(100, 400, 200, 25);
        viewOdetails.setBounds(100,285,200,25);
        panel.add(viewOdetails);
        panel.add(viewTable);
        panel.add(devTable);
        panel.add(ordersTable);
        panel.add(productsTable);
        panel.add(deleteCustomer);
        panel.add(deleteDeveloper);
        panel.add(sendOrder);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        actionAdmin();
    }

    public void actionAdmin() {
        deleteCustomer.addActionListener(new ActionListener() { //button uses selected cell to delete from SQL table at that selected value
            public void actionPerformed(ActionEvent ae) {
                int row = buildView.getSelectedRow();
                int column = buildView.getSelectedColumn();
                String name = (String) buildView.getValueAt(row, column);
                try {
                    PreparedStatement deleteCustomer = c.prepareStatement("delete from customers where email = ?");  //Prepared Statement for adding to customer table
                    deleteCustomer.setString(1, name);
                    deleteCustomer.executeUpdate();
                    dispose();
                    new AdministratorView();

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null,"Select Email in Table");
                }
            }
        });
        deleteDeveloper.addActionListener(new ActionListener() { //button uses selected cell to delete from SQL table at that selected value
            public void actionPerformed(ActionEvent ae) {
                int row = developerTAble.getSelectedRow();
                int column = developerTAble.getSelectedColumn();
                String name = (String) developerTAble.getValueAt(row, column);
                try {
                    PreparedStatement deleteDeveloper = c.prepareStatement("delete from developers where name = ?");  //Prepared Statement for adding to customer table
                    deleteDeveloper.setString(1, name);
                    deleteDeveloper.executeUpdate();
                    dispose();
                    new AdministratorView();

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null,"Select Email in Table");
                }
            }
        });
        sendOrder.addActionListener(new ActionListener() { //button uses selected cell to delete from SQL table at that selected value
            public void actionPerformed(ActionEvent ae) {
                int row = buildOrders.getSelectedRow();
                int column = buildOrders.getSelectedColumn();
                BigDecimal name = (BigDecimal) buildOrders.getValueAt(row, column);
                o_id = name;

                try {
                    PreparedStatement sendOrder = c.prepareStatement("UPDATE orders SET shipped = ? WHERE o_id = ?");  //Prepared Statement for adding to customer table
                    sendOrder.setString(1, "2018-14-04");
                    sendOrder.setBigDecimal(2, name);
                    sendOrder.executeUpdate();
                    dispose();
                    new AdministratorView();

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null,"Select Email in Table");
                }
            }
        });
        viewOdetails.addActionListener(new ActionListener() { //button uses selected cell to delete from SQL table at that selected value
            public void actionPerformed(ActionEvent ae) {
                int row = buildOrders.getSelectedRow();
                int column = buildOrders.getSelectedColumn();
                BigDecimal name = (BigDecimal) buildOrders.getValueAt(row, column);
                o_id = name;
                try {
                    Statement s4 = c.createStatement();
                    ResultSet rs5 = s4.executeQuery("select * from odetails where o_id = '"+ o_id +"'");
                    JTable buildOdetails = new JTable(Login.buildTableModel(rs5));
                    JScrollPane odetailsTable =  new JScrollPane(buildOdetails, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    odetailsTable.setBounds(350, 365, 500, 100);
                    panel.add(odetailsTable);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}