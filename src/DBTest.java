import java.sql.Connection;
import java.sql.DriverManager;

public class DBTest {

    public static void main(String[] args) {

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vendor_db",
                    "root",
                    "root"
            );

            if (con != null) {
                System.out.println("Database Connected Successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

