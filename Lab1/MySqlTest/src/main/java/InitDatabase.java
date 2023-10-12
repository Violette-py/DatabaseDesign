import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InitDatabase {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String BASE_JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER_NAME = "root";
    private static final String USER_PWD = "gansui";

    public static void init() throws Exception {
        Connection conn = getConn(""); // 只连接到主机，暂时未创建数据库

        // 读取并执行创建数据库和表的SQL文件
        String initSQLFilePath = "src/main/sql/init.sql";
        Statement stmt = conn.createStatement();
        executeSQLFromFile(initSQLFilePath, stmt);

        System.out.println("成功创建数据库和表");
    }

    public static Connection getConn(String databaseName) {

        String jdbcUrl = BASE_JDBC_URL + databaseName;
        System.out.println("jdbcUrl : " + jdbcUrl);

        Connection conn = null;
        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(jdbcUrl.trim(), USER_NAME, USER_PWD);
            System.out.println("数据库连接成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("数据库连接失败");
        }
        return conn;
    }

    // 读取 SQL文件并执行：创建数据库 & 创建表
    private static void executeSQLFromFile(String filePath, Statement statement) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
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