import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperation {
    public static List<Course> findCouInfo() {
        List<Course> courses = new ArrayList<Course>();
        Connection conn = LinkDatabase.getConn();
        if (conn == null) {
            return null;
        }
        String sql = "select * from course ";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                int no = results.getInt("no");
                String name = results.getString("name");
                double score = results.getDouble("score");
                Course c = new Course(no, name, score);
                courses.add(c);
            }
            // 尚未释放资源

//            //5.获取执行sql的对象 Statement
//            Statement stmt = conn.createStatement();
//            //6.执行sql
//            int count = stmt.executeUpdate(sql);
//            //7.处理结果
//            System.out.println(count);
//            //8.释放资源
//            stmt.close();
//            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return courses;
    }
}
