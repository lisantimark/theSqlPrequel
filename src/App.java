import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by owmer on 3/22/2018.
 */
public class App {

    public static Connection SQLiteJDBC() {
            Connection c = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/prequelSql");
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Opened database successfully");
            return c;
        }
/*
    public static void loadDriver() {
        try {
            // this will load the MySQL driver
            // make sure the corresponding jar file is included on the java CLASSPATH
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver instance ok...");
        }
        catch (Exception e) {
            System.err.println("Unable to load driver.");
            e.printStackTrace();
        }
    }

    //Establish a connection with specified database. Return connection object
    public static Connection establish_connection(String database_name, String sql_username, String sql_passwd)
    {
        Connection conn = null;
        try {

            System.out.println("Establishing connection with MySql server on satoshi..");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://152.20.12.152/"+database_name+"?noAccessToProcedureBodies=true"+"&useSSL=false"+"&user="+sql_username+"&password="+sql_passwd);

            System.out.println("Connection with MySql server on satoshi.cis.uncw.edu established.");
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState:     " + e.getSQLState());
            System.out.println("VendorError:  " + e.getErrorCode());
        }

        return conn;
    }

*/

    public static void main(String[] args) {

        //loadDriver();
        //establish_connection("DATABASE NAME", "USER NAME", "PASSWORD);
        Login frameTable = new Login();

    }
}
