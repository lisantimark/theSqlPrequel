/**
 * Created by owmer on 3/25/2018.
 */
import javax.swing.*;
import java.sql.*;

public class AdministratorView extends JFrame {

    public static void main(String[] args) throws SQLException {
        new AdministratorView();
    }

    Connection c = Login.SQLiteJDBC();
    Statement s = c.createStatement();
    Statement s2 = c.createStatement();
    ResultSet rs = s2.executeQuery("select * from customers_cv");
    ResultSet rs2 = s.executeQuery("select * from developers");
    JLabel welcome = new JLabel("Testing");
    JPanel panel = new JPanel();
    JTable buildView = new JTable(Login.buildTableModel(rs));
    JTable developerTAble = new JTable(Login.buildTableModel(rs2));
    JScrollPane viewTable =  new JScrollPane(buildView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JScrollPane devTable =  new JScrollPane(developerTAble, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    AdministratorView() throws SQLException {
        super("Admin View");
        setSize(900,900);
        setLocation(0,0);
        panel.setLayout(null);
        welcome.setBounds(70,50,150,60);
        viewTable.setBounds(350, 50, 500, 100);
        devTable.setBounds(350, 200, 500, 100);
        panel.add(welcome);
        panel.add(viewTable);
        panel.add(devTable);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}