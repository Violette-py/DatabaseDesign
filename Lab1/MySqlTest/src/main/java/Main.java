import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入数据库用户名: ");
        String username = scanner.nextLine();

        System.out.print("请输入数据库密码: ");
        String password = scanner.nextLine();


        try {
            InitDatabase.init(username, password);

            DatabaseOperation.insertCsvData("room", "GBK");
            DatabaseOperation.insertCsvData("student", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Arrays.fill(password.toCharArray(), ' '); // 清除密码
        }

        // 关闭 Scanner
        scanner.close();

    }
}