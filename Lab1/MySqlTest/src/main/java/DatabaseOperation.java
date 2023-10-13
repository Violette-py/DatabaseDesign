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

            String csvFilePath = BASE_CSV_FILE_PATH + tableName + ".csv";
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName(charsetName)); //"GBK"  "utf-8"
            reader.readHeaders(); // 读取 csv文件的标题行，即列名

            int columnLen = reader.getHeaderCount();
            String columnName = new BufferedReader(new FileReader(new File(csvFilePath))).readLine();
            columnName = columnName.replace("\"", ""); // 去除列名中的双引号，否则 SQL语句有语法错误

            System.out.println("columnName : " + columnName);

            while (reader.readRecord()) {

                String insertSQL = "INSERT IGNORE INTO " + tableName + " (" + columnName + ") VALUES (";
                for (int i = 0; i < columnLen; i++) {
                    if (i > 0) {
                        insertSQL += ",";
                    }
                    insertSQL += "?"; // 使用问号作为参数占位符
                }
                insertSQL += ")";

                // 执行参数化查询，比手动对特殊字符转义更安全，避免 SQL 注入
                PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);
                for (int i = 0; i < columnLen; i++) {
                    String columnValue = reader.get(i);
                    if (columnValue.isEmpty()) {
                        preparedStatement.setNull(i + 1, Types.VARCHAR); // 如果值为空，设置为 NULL
                    } else {
                        preparedStatement.setString(i + 1, columnValue); // 设置参数值
                    }
                }
                preparedStatement.execute();

                // 检查主键冲突，若存在则提示用户后，继续执行后续操作
                int affectedRows = preparedStatement.getUpdateCount();
                if (affectedRows == 0) {
                    System.out.println("主键冲突，未插入： " + preparedStatement.toString());  // insertSQL
                }
            }

            // 释放资源
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
