// Course Object

package a1_2301040086;

public class Course {
    private String course_id;
    private String course_name;

    public Course(String course_id, String course_name) {
        this.course_id = course_id;
        this.course_name = course_name;
    }

    public String getCourseID(){
        return course_id;
    }

    public String getCourseName(){
        return course_name;
    }
}
