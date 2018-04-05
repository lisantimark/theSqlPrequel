/**
 * Created by owmer on 3/25/2018.
 */
import javax.swing.*;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;

public class NewCustomer extends JFrame {

    Connection c = Login.SQLiteJDBC();
    JPanel panel = new JPanel();
    JTextField txuser = new JTextField();
    JButton create = new JButton("Create Account");
    JLabel done = new JLabel("Customer Account Added");
    JTextField pass = new JTextField();
    JTextField name = new JTextField();
    JTextField address = new JTextField();
    JTextField phone = new JTextField();
    JTextField cc = new JTextField();
    JTextField ce = new JTextField();
    JTextField sc = new JTextField();
    JTextField zip = new JTextField();

    NewCustomer() {
        super("Create Customer Account");
        setSize(600, 400);
        setLocation(550, 320);
        panel.setLayout(null);
        txuser.setBounds(300, 30, 200, 20);
        pass.setBounds(300, 65, 200, 20);
        name.setBounds(300, 100, 200, 20);
        address.setBounds(300, 135, 200, 20);
        phone.setBounds(300, 170, 200, 20);
        cc.setBounds(300, 205, 200, 20);
        ce.setBounds(300, 240, 200, 20);
        sc.setBounds(300, 275, 200, 20);
        zip.setBounds(300, 310, 200, 20);
        create.setBounds(75, 125, 200, 100);
        done.setBounds(85, 200, 200, 100);
        panel.add(create);
        panel.add(txuser);
        panel.add(pass);
        panel.add(name);
        panel.add(address);
        panel.add(phone);
        panel.add(cc);
        panel.add(ce);
        panel.add(sc);
        panel.add(zip);
        txuser.setText("Enter your email");
        pass.setText("Enter your password");
        name.setText("Enter your name");
        address.setText("Enter your address");
        phone.setText("Phone ex: 9198026745");
        cc.setText("Credit card ex: 1111222233334444");
        ce.setText("Card expiration ex: 2016-05-02");
        sc.setText("Card security code");
        zip.setText("Zip code");
        getContentPane().add(panel);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        actionlogin();
    }

    public void actionlogin() {
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    Statement newID = c.createStatement();
                    ResultSet rs = newID.executeQuery("select max(c_id) from customers");
                    int id = rs.getInt(1) + 1;
                    PreparedStatement newCust = c.prepareStatement("insert into customers values(?,?,?,?,?,?,?,?,?,?)");  //Prepared Statement for adding to customer table
                    newCust.setBigDecimal(1, BigDecimal.valueOf(id)); //Sets the string to a NUMERIC type
                    newCust.setString(2, txuser.getText());
                    newCust.setString(3, pass.getText());
                    newCust.setString(4, name.getText());
                    newCust.setString(5, address.getText());
                    newCust.setBigDecimal(6, BigDecimal.valueOf(Long.parseLong(phone.getText())));
                    newCust.setBigDecimal(7, BigDecimal.valueOf(Long.parseLong(cc.getText())));
                    newCust.setString(8, ce.getText());                                             //**Coudln't get setDate to work, maybe need to parse the string to get the date type to accept?
                    newCust.setBigDecimal(9, BigDecimal.valueOf(Long.parseLong(sc.getText())));
                    newCust.setBigDecimal(10, BigDecimal.valueOf(Long.parseLong(zip.getText())));
                    newCust.executeUpdate();
                    dispose();

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null,"Invalid Entry / Please Re-enter Password / Check Fields");
                    pass.setText("");
                    pass.requestFocus();
                }
            }
        });
    }
}