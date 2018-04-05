/**
 * Created by owmer on 3/24/2018.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;


public class Login extends JFrame {

    Connection c = SQLiteJDBC();
    JButton admin = new JButton("Administrator Login");
    JButton cust = new JButton("Customer Login");
    JButton create = new JButton("Create Account");
    JPanel panel = new JPanel();
    JTextField txuser = new JTextField();
    JPasswordField pass = new JPasswordField();

    Login() {
        super("Login Autentification");
        setSize(600,275);
        setLocation(500,280);
        panel.setLayout (null);
        txuser.setBounds(225,30,150,20);
        pass.setBounds(225,65,150,20);
        cust.setBounds(225,100,150,20);
        panel.add(cust);
        create.setBounds(225,125,150,20);
        panel.add(create);
        admin.setBounds(225,150, 150,20);
        panel.add(admin);
        panel.add(txuser);
        panel.add(pass);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        actionlogin();
    }

    public void actionlogin() {
        cust.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String puname = txuser.getText();
                String ppaswd = pass.getText();
                String s = null;
                String s2 = null;
                try {
                    PreparedStatement checkLogin = c.prepareStatement("select email, password from customers where email = ?");
                    checkLogin.setString(1, puname);
                    ResultSet rs = checkLogin.executeQuery();
                    s = rs.getString(1);
                    s2 = rs.getString(2);
                } catch (SQLException e) {
                }
                if (puname.equals(s) && ppaswd.equals(s2)) {
                    CustomerView regFace = null;
                    try {
                        regFace = new CustomerView();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    regFace.setVisible(true);
                    //dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Password / Username");
                    txuser.setText("");
                    pass.setText("");
                    txuser.requestFocus();
                }
            }

        });
        admin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String puname = txuser.getText();
                String ppaswd = pass.getText();
                String s = null;
                String s2 = null;
                try {
                    PreparedStatement checkLogin = c.prepareStatement("select email, password from admins where email = ?");
                    checkLogin.setString(1, puname);
                    ResultSet rs = checkLogin.executeQuery();
                    s = rs.getString(1);
                    s2 = rs.getString(2);
                } catch (SQLException e) {
                }
                if(puname.equals(s) && ppaswd.equals(s2)) {
                    AdministratorView regFace = null;
                    try {
                        regFace = new AdministratorView();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    regFace.setVisible(true);
                    //dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null,"Wrong Password / Username");
                    txuser.setText("");
                    pass.setText("");
                    txuser.requestFocus();
                }
            }
        });
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new NewCustomer();
            }
        });
    }

    public static Connection SQLiteJDBC() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/prequelSql");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        //System.out.println("Opened database successfully");
        return c;
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    public static void main(String[] args) {
        new Login();
    }
}
