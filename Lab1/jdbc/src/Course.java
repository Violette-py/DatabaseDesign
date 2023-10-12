
public class Course {
    private int no;
    private String name;
    private double score;

    public Course(int no, String name, double score) {
        this.no = no;
        this.name = name;
        this.score = score;
    }

    public Course(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Course{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}