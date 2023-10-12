import com.csvreader.CsvReader;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

                String insertSQL = "insert ignore " + tableName + "(" + columnName + ") values('" + reader.get(0) + "'";
//                String insertSQL = "insert into " + tableName + "(" + columnName + ") values(" + reader.get(0);
                for (int i = 1; i < columnLen - 1; i++) {
                    insertSQL += "," + "'" + reader.get(i).replaceAll("'", "\\\\'") + "'";
                }
                insertSQL += "," + "'" + reader.get(columnLen - 1).replaceAll("'", "\\\\'") + "');";

//                System.out.println("insertSQL : " + insertSQL);

                stmt.execute(insertSQL);

                int affectedRows = stmt.getUpdateCount();
                if (affectedRows == 0) {
                    // 主键冲突，提示用户后继续插入
                    System.out.println("插入失败：主键冲突" + insertSQL);
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
