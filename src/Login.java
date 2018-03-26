/**
 * Created by owmer on 3/24/2018.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Login extends JFrame {
    Connection c = App.SQLiteJDBC();
    JButton admin = new JButton("Administrator Login");
    JButton cust = new JButton("Customer Login");
    JButton create = new JButton("Create Account");
    JPanel panel = new JPanel();
    JTextField txuser = new JTextField(45);
    JPasswordField pass = new JPasswordField(45);

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
                Statement stmt = null;
                String s = null;
                String s2 = null;
                try {
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("select email, password from customers where email LIKE '%" + puname + "%'");
                    s = rs.getString(1);
                    s2 = rs.getString(2);
                } catch (SQLException e) {
                }

                if (puname.equals(s) && ppaswd.equals(s2)) {
                    CustomerView regFace = new CustomerView();
                    regFace.setVisible(true);
                    dispose();
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
                Statement stmt = null;
                String s = null;
                String s2 = null;
                try {
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("select email, password from admins where email LIKE '%" + puname + "%'");
                    s = rs.getString(1);
                    s2 = rs.getString(2);
                } catch (SQLException e) {
                }
                if(puname.equals(s) && ppaswd.equals(s2)) {
                    AdministratorView regFace = new AdministratorView();
                    regFace.setVisible(true);
                    dispose();
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
                String puname = txuser.getText();
                String ppaswd = pass.getText();
            }
        });
    }
}
