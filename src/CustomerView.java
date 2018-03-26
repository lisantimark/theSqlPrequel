/**
 * Created by owmer on 3/24/2018.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CustomerView extends JFrame {

    public static void main(String[] args) {
        CustomerView frameTable = new CustomerView();
    }

    JLabel welcome = new JLabel("WOO CUSTOMER VIEW");
    JPanel panel = new JPanel();

    CustomerView() {
        super("Customer View");
        setSize(600,275);
        setLocation(500,280);
        panel.setLayout (null);
        welcome.setBounds(70,50,150,60);
        panel.add(welcome);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
