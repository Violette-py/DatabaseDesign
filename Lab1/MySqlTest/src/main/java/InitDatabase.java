import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InitDatabase {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String BASE_JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String INIT_SQL_FILE_PATH = "src/main/sql/init.sql";
    private static String username;
    private static String password;

    public static void init(String in_username, String in_password) throws Exception {

        // 保存用户名和密码
        username = in_username;
        password = in_password;

        Connection conn = getConn(""); // 只连接到主机，暂时未创建数据库

        // 读取并执行创建数据库和表的SQL文件
        Statement stmt = conn.createStatement();
        createDatabaseAndTable(stmt);

        System.out.println("成功创建数据库和表");
    }

    public static Connection getConn(String databaseName) {

        String jdbcUrl = BASE_JDBC_URL + databaseName;

        Connection conn = null;
        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(jdbcUrl.trim(), username, password);
            System.out.println("数据库连接成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("数据库连接失败");
        }
        return conn;
    }

    // 读取创建数据库和创建表的 SQL文件，并执行
    private static void createDatabaseAndTable(Statement statement) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INIT_SQL_FILE_PATH));
        String line;
        StringBuilder sqlStatement = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sqlStatement.append(line).append(" ");
        }

        br.close();

        String[] sqlStatements = sqlStatement.toString().split(";");
        for (String sql : sqlStatements) {
            if (!sql.trim().isEmpty()) {
                statement.execute(sql);
            }
        }
    }
}