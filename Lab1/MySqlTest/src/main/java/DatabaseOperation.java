import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;

import com.csvreader.CsvReader;
import org.mozilla.universalchardet.UniversalDetector;

public class DatabaseOperation {
    private static final String DATABASE_NAME = "exam";
    private static final String BASE_CSV_FILE_PATH = "src/main/resources/data/";

    public static void insertCsvData(String tableName) {
        try {
            Connection conn = InitDatabase.getConn(DATABASE_NAME);
            if (conn == null) {
                return;
            }

            // 识别 csv文件编码格式
            String csvFilePath = BASE_CSV_FILE_PATH + tableName + ".csv";
            String detectedCharset = getFileEncode(csvFilePath);

            // 读取 csv文件
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName(detectedCharset));
            reader.readHeaders(); // 读取 csv文件的标题行，即列名

            String columnName = new BufferedReader(new FileReader(new File(csvFilePath))).readLine();
            columnName = columnName.replace("\"", ""); // 去除列名中的双引号，否则 SQL语句有语法错误

            while (reader.readRecord()) {
                String insertSQL = generateInsertSQL(tableName, columnName, reader.getHeaderCount());
                executeInsertStatement(conn, insertSQL, reader);
            }

            System.out.println("CSV data of " + tableName + " has been inserted successfully.");

            // 关闭数据库连接
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 自动识别 csv文件的编码格式
    public static String getFileEncode(String filePath) throws IOException {
        FileInputStream in = new FileInputStream(new File(filePath));
        String code = "utf-8";
        byte[] buf = new byte[4096];
        UniversalDetector detector = new UniversalDetector(null);

        int nread;
        while ((nread = in.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        detector.dataEnd();

        String encoding = detector.getDetectedCharset();
        if (!encoding.isEmpty()) {
            code = encoding;
        }
        in.close();
        return code;
    }

    // 组织 SQL代码
    private static String generateInsertSQL(String tableName, String columnName, int columnCount) {
        StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableName + " (" + columnName + ") VALUES (");
        for (int i = 0; i < columnCount; i++) {
            if (i > 0) {
                insertSQL.append(",");
            }
            insertSQL.append("?"); // 使用问号作为参数占位符
        }
        insertSQL.append(")");
        return insertSQL.toString();
    }

    // 执行参数化查询，比手动对特殊字符转义更安全，避免 SQL 注入
    private static void executeInsertStatement(Connection conn, String insertSQL, CsvReader reader) {
        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = conn.prepareStatement(insertSQL);
            int columnLen = reader.getHeaderCount();

            for (int i = 0; i < columnLen; i++) {
                String columnValue = reader.get(i);
                if (columnValue.isEmpty()) {
                    preparedStatement.setNull(i + 1, Types.VARCHAR);  // 空串置为 null
                } else {
                    preparedStatement.setString(i + 1, columnValue);
                }
            }
            preparedStatement.execute();

        } catch (SQLIntegrityConstraintViolationException e) {
            if (e.getMessage().contains("PRIMARY")) {
                System.out.println("主键冲突，未插入： " + preparedStatement.toString());
            } else if (e.getMessage().contains("FOREIGN")) {
                System.out.println("外键冲突，未插入： " + preparedStatement.toString());
            }
        } catch (SQLException e) {
            System.out.println("SQL错误：" + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
