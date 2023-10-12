import java.sql.Connection;
import java.sql.DriverManager;

public class LinkDatabase {
    public static Connection getConn() {
        // TODO Auto-generated method stub
        String driverName = "com.mysql.cj.jdbc.Driver";
        //String driverName="org.git.mm.mysql.Driver";
        String dbURL = "jdbc:mysql://localhost:3306/courseinfo";
        String userName = "root";
        String userPwd = "gansui";
        Connection conn = null;
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("数据库连接成功");
        } catch (Exception e) {
            System.out.println("数据库连接失败");
        }
        return conn;
    }
}
