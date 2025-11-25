package a1_2301040086;

public class Score {
    private int id;
    private String stu_id;
    private String course_id;
    private double score;

    public Score(int id, String stu_id, String course_id, double score){
        this.id = id;
        this.stu_id = stu_id;
        this.course_id = course_id;
        this.score = score;
    }

    public int getID() {
        return id;
    }

    public String getStuID() {
        return stu_id;
    }

    public String getCourseID() {
        return course_id;
    }

    public double getScore() {
        return score;
    }

}
