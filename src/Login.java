
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


public class Login extends JFrame { //Login in class, creates view on login info

    Connection c = JDBC();
    JButton admin = new JButton("Administrator Login");
    JButton cust = new JButton("Customer Login");
    JButton create = new JButton("Create Account");
    JPanel panel = new JPanel();
    JTextField txuser = new JTextField();
    JPasswordField pass = new JPasswordField();
    public static String puname;

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

    public void actionlogin() { //Customer Login Button
        cust.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                puname = txuser.getText();
                String ppaswd = pass.getText();
                try {
                    CallableStatement getLogin = c.prepareCall("{? = call checkUser(?,?)}"); //Stored function returns 1 or 0
                    getLogin.registerOutParameter(1, java.sql.Types.INTEGER);
                    getLogin.setString(2, puname);
                    getLogin.setString(3, ppaswd);
                    getLogin.execute();
                    int test = getLogin.getInt(1);
                    if (test == 1) {                                            // 1 = pass allowing login
                        System.out.println("Launch Customer View");
                        CustomerView regFace = new CustomerView();
                        regFace.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong Password / Username");
                        txuser.setText("");
                        pass.setText("");
                        txuser.requestFocus();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        admin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                puname = txuser.getText();
                String ppaswd = pass.getText();
                try {
                    CallableStatement getLogin = c.prepareCall("{? = call checkAdmin(?,?)}");  //Stored function returns 1 or 0
                    getLogin.registerOutParameter(1, java.sql.Types.INTEGER);
                    getLogin.setString(2, puname);
                    getLogin.setString(3, ppaswd);
                    getLogin.execute();
                    int test = getLogin.getInt(1);
                    if (test == 1) {                                                        // 1 = pass allowing login
                        System.out.println("Launch Admin View");
                        AdministratorView regFace = new AdministratorView();
                        regFace.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong Password / Username");
                        txuser.setText("");
                        pass.setText("");
                        txuser.requestFocus();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new NewCustomer();
            }
        });
    }

    public static Connection JDBC() { //This Function is called in all other classes, creates connection to local mySql server this
        Connection conn = null;
        try {

            System.out.println("Establishing connection with MySql server on localhost..");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test"+"?noAccessToProcedureBodies=true"+"&useSSL=false"+"&user="+"test"+"&password="+"password");

            System.out.println("Connection with MySql server on localhost connected...");
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState:     " + e.getSQLState());
            System.out.println("VendorError:  " + e.getErrorCode());
        }
        return conn;
    }

    public static DefaultTableModel buildTableModel(ResultSet rs) //This Function is called in all other classes, used for visually displaying tables
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

    public static void main(String[] args) throws SQLException {
        new Login();
    }
}
