package a1_2301040086;

public class Student {
    private String stu_id;
    private String stu_fname;

    public Student(String stu_id, String stu_fname){
        this.stu_id = stu_id;
        this.stu_fname = stu_fname;

    }

    public String getStudentID() {
        return stu_id;
    }

    public String getStudentName() {
        return stu_fname;
    }
}
