/**
 * Created by owmer on 3/25/2018.
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdministratorView extends JFrame {

    public static void main(String[] args) throws SQLException {
        new AdministratorView();
    }

    Connection c = Login.SQLiteJDBC();
    Statement s = c.createStatement(); //need to reduce redundancy in creating statements
    Statement s2 = c.createStatement();
    Statement s3 = c.createStatement();
    ResultSet rs = s2.executeQuery("select * from customers_cv");
    ResultSet rs2 = s.executeQuery("select * from developers");
    ResultSet rs3 = s3.executeQuery("select * from products");
    JButton deleteCustomer = new JButton("Delete Customer");
    JButton deleteDeveloper = new JButton("Delete Developer");
    JButton deleteProduct = new JButton("Delete Product");
    JPanel panel = new JPanel();
    JTable buildView = new JTable(Login.buildTableModel(rs));
    JTable developerTAble = new JTable(Login.buildTableModel(rs2));
    JTable buildProducts = new JTable(Login.buildTableModel(rs3));
    JScrollPane viewTable =  new JScrollPane(buildView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); //Tables for 'admin' views
    JScrollPane devTable =  new JScrollPane(developerTAble, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JScrollPane productsTable =  new JScrollPane(buildProducts, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    AdministratorView() throws SQLException {
        super("Admin View");
        setSize(900,900);
        setLocation(0,0);
        panel.setLayout(null);
        viewTable.setBounds(350, 50, 500, 100);
        devTable.setBounds(350, 200, 500, 100);
        productsTable.setBounds(350, 350, 500, 100);
        deleteCustomer.setBounds(100, 75, 200, 50);
        deleteDeveloper.setBounds(100, 225, 200, 50);
        deleteProduct.setBounds(100, 375, 200, 50);
        panel.add(viewTable);
        panel.add(devTable);
        panel.add(productsTable);
        panel.add(deleteCustomer);
        panel.add(deleteDeveloper);
        panel.add(deleteProduct);
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

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null,"Select Email in Table");
                }
                viewTable.repaint(); //Can't get tables to repaint/ refresh need to fix
            }
        });

        deleteDeveloper.addActionListener(new ActionListener() { //button uses selected cell to delete from SQL table at that selected value
            public void actionPerformed(ActionEvent ae) {
                int row = developerTAble.getSelectedRow();
                int column = developerTAble.getSelectedColumn();
                String name = (String) developerTAble.getValueAt(row, column);
                try {
                    PreparedStatement deleteDeveloper = c.prepareStatement("delete from developers where d_id = ?");  //Prepared Statement for adding to customer table
                    deleteDeveloper.setString(1, name);
                    deleteDeveloper.executeUpdate();

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null,"Select Email in Table");
                }
                developerTAble.repaint(); //Can't get tables to repaint/ refresh need to fix

            }
        });
        deleteProduct.addActionListener(new ActionListener() { //button uses selected cell to delete from SQL table at that selected value
            public void actionPerformed(ActionEvent ae) {
                int row = buildProducts.getSelectedRow();
                int column = buildProducts.getSelectedColumn();
                String name = (String) buildProducts.getValueAt(row, column);
                try {
                    PreparedStatement deleteProduct = c.prepareStatement("delete from products where p_id = ?");  //Prepared Statement for adding to customer table
                    deleteProduct.setString(1, name);
                    deleteProduct.executeUpdate();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null,"Select Email in Table");
                }
                buildProducts.repaint(); //Can't get tables to repaint/ refresh need to fix
            }
        });
    }


}