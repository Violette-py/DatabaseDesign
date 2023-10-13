import com.csvreader.CsvReader;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;

public class DatabaseOperation {

    private static final String DATABASE_NAME = "exam";
    private static final String BASE_CSV_FILE_PATH = "src/main/resources/data/";

    public static void insertCsvData(String tableName, String charsetName) {
        try {
            Connection conn = InitDatabase.getConn(DATABASE_NAME);
            if (conn == null) {
                return;
            }

            Statement stmt = conn.createStatement();

            // 从csv文件中读取数据
            String csvFilePath = BASE_CSV_FILE_PATH + tableName + ".csv";
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName(charsetName)); //"GBK"  "utf-8"
            reader.readHeaders(); // 读取 csv文件的标题行，即列名

            String columnName = new BufferedReader(new FileReader(new File(csvFilePath))).readLine();
            columnName = columnName.replace("\"", ""); // 去除列名中的双引号，否则 SQL语句有语法错误

            while (reader.readRecord()) {
                String insertSQL = generateInsertSQL(tableName, columnName, reader.getHeaderCount());
                executeInsertStatement(conn, insertSQL, reader);
            }

            System.out.println("CSV data of " + tableName + " has been inserted successfully.");

            // 释放资源
            stmt.close();
            conn.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 组织 SQL代码
    private static String generateInsertSQL(String tableName, String columnName, int columnCount) {
        StringBuilder insertSQL = new StringBuilder("INSERT IGNORE INTO " + tableName + " (" + columnName + ") VALUES (");
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
    private static void executeInsertStatement(Connection conn, String insertSQL, CsvReader reader) throws Exception {
        PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);
        int columnLen = reader.getHeaderCount();

        for (int i = 0; i < columnLen; i++) {
            String columnValue = reader.get(i);
            if (columnValue.isEmpty()) {
                preparedStatement.setNull(i + 1, Types.VARCHAR);
            } else {
                preparedStatement.setString(i + 1, columnValue);
            }
        }
        preparedStatement.execute();
        int affectedRows = preparedStatement.getUpdateCount();
        if (affectedRows == 0) {
            System.out.println("主键冲突，未插入： " + preparedStatement.toString());
        }
    }
}
