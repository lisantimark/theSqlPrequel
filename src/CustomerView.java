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
        CallableStatement cStmt = null;

    }




    Connection c = Login.JDBC();

    Statement s = c.createStatement();
    ResultSet rs = s.executeQuery("select * from products");
    ResultSet rs2 = s.executeQuery("select * from admin_cv");
    //ResultSet rs3 = s3.executeQuery("select * from admin_cv");
    JPanel panel = new JPanel();
    JTable productsTable = new JTable(Login.buildTableModel(rs));
    JTable buildView = new JTable(Login.buildTableModel(rs2));
    JScrollPane currentTable =  new JScrollPane(productsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); //Going to change tables displayed in this class
    JScrollPane viewTable =  new JScrollPane(buildView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    String[] menuOptions = { "All Products", "Developer", "Format", "Top Rating", "Price Low to High", "Price High to Low" };
    JComboBox searchMenu = new JComboBox(menuOptions);

    CustomerView() throws SQLException {
        super("Customer View");

        setSize(900, 900);
        setLocation(0, 0);
        panel.setLayout(null);
        searchMenu.setBounds(70, 500, 150, 50);
        currentTable.setBounds(50, 600, 800, 250);
        viewTable.setBounds(350, 50, 500, 100);
        panel.add(searchMenu);
        panel.add(currentTable);
        panel.add(viewTable);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //public void actionlogin() {
      //  admin_view.addActionListener(new ActionListener() {
        //    public void actionPerformed(ActionEvent ae) {
          //      currentQuery = "select * from admin_cv";
            //    panel.remove(currentTable);
              //  panel.repaint();
                //}
           // });
   // }
}
