import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // 读取配置文件
        Config config = new Config("src/main/resources/config.properties");
        String dbHost = config.getDatabaseHost();
        int dbPort = config.getDatabasePort();
        String dbUsername = config.getDatabaseUsername();
        String dbPassword = config.getDatabasePassword();

        try {
            // 创建数据库和数据表
            InitDatabase.init(dbHost, dbPort, dbUsername, dbPassword);

            // 从csv文件读取数据并插入
            DatabaseOperation.insertCsvData("room");
            DatabaseOperation.insertCsvData("student");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}