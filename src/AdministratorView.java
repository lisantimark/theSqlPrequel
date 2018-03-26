/**
 * Created by owmer on 3/25/2018.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class AdministratorView extends JFrame {

    public static void main(String[] args) {
        AdministratorView frameTable = new AdministratorView();
    }

    JLabel welcome = new JLabel("Yeahhh Budddyyy");
    JPanel panel = new JPanel();

    AdministratorView() {
        super("Admin View");
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