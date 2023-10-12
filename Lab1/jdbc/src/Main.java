import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Course> courses = new ArrayList<Course>();
        courses = DatabaseOperation.findCouInfo();
        for (Course c : courses) {
            System.out.println(c);
        }
    }
}