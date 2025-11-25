package a1_2301040086;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private static final String dblink = "jdbc:sqlite:src/a1_2301040086/data.db";


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dblink);
    }

    public static void startData(){
        try (Connection connect = getConnection()) {
            Statement state1 = connect.createStatement();

            state1.execute(
                    "CREATE TABLE IF NOT EXISTS Students (" +
                            "stu_id TEXT PRIMARY KEY, " +
                            "stu_fname TEXT NOT NULL)"
            );

            state1.execute(
                    "CREATE TABLE IF NOT EXISTS Course (" +
                            "course_id TEXT PRIMARY KEY, " +
                            "course_name TEXT NOT NULL)"
            );

            state1.execute(
                    "CREATE TABLE IF NOT EXISTS Scores (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "stu_id TEXT NOT NULL, " +
                            "course_id TEXT NOT NULL, " +
                            "score REAL NOT NULL CHECK(score >= 0 AND score <= 10), " +
                            "FOREIGN KEY (stu_id) REFERENCES Students(stu_id) ON DELETE CASCADE, " +
                            "FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE, " +
                            "UNIQUE(stu_id, course_id))"
            );
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Database initialization failed: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //!!![STUDENTS]!!!!
    public static List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT stu_id, stu_fname FROM Students ORDER BY stu_id";

        try (Connection connect = getConnection();
             Statement state2 = connect.createStatement();
             ResultSet result = state2.executeQuery(sql)) {

            while (result.next()) {
                students.add(new Student(
                                result.getString("stu_id"),
                                result.getString("stu_fname")
                        )
                );
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static boolean addStudents(String stuID, String stuFName) throws SQLException{
        String sql = "INSERT INTO Students (stu_id, stu_fname) VALUES (?, ?)";

        try (Connection connect = getConnection();
             PreparedStatement prestate = connect.prepareStatement(sql)) {

            prestate.setString(1, stuID);
            prestate.setString(2, stuFName);
            prestate.executeUpdate();
            return true;
        }
    }

    public static boolean deleteStudent(String stuID) throws SQLException {
        String sql = "DELETE FROM Students WHERE stu_id = ?";

        try (Connection connect = getConnection();
             PreparedStatement prestate2 = connect.prepareStatement(sql)) {

            prestate2.setString(1, stuID);
            int affected = prestate2.executeUpdate();
            return affected > 0;
        }

    }


    //!!![COURSE]!!! the same as Student
    public static List<Course> getCourse() {
        List<Course> Course = new ArrayList<>();
        String sql = "SELECT course_id, course_name FROM Course ORDER BY course_id";

        try (Connection connect = getConnection();
             Statement state2 = connect.createStatement();
             ResultSet result = state2.executeQuery(sql)) {

            while (result.next()) {
                Course.add(new Course(
                                result.getString("course_id"),
                                result.getString("course_name")
                        )
                );
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return Course;
    }

    public static boolean addCourse(String courseID, String courseName) throws SQLException{
        String sql = "INSERT INTO Course (course_id, course_name) VALUES (?, ?)";

        try (Connection connect = getConnection();
             PreparedStatement prestate = connect.prepareStatement(sql)) {

            prestate.setString(1, courseID);
            prestate.setString(2, courseName);
            prestate.executeUpdate();
            return true;
        }
    }

    public static boolean deleteCourse(String courseID) throws SQLException {
        String sql = "DELETE FROM Course WHERE course_id = ?";

        try (Connection connect = getConnection();
             PreparedStatement prestate2 = connect.prepareStatement(sql)) {

            prestate2.setString(1, courseID);
            int affected = prestate2.executeUpdate();
            return affected > 0;

        }
    }

    //!!![SCORES]!!! the same
    public static List<Score> getScores() {
        List<Score> scores = new ArrayList<>();
        String sql = "SELECT id, stu_id, course_id, score FROM Scores ORDER BY id ";

        try (Connection connect = getConnection();
             Statement state = connect.createStatement();
             ResultSet result = state.executeQuery(sql)) {

            while (result.next()) {
                scores.add(new Score(
                                result.getInt("id"),
                                result.getString("stu_id"),
                                result.getString("course_id"),
                                result.getDouble("score")
                        )
                );

            }
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return scores;
    }

    public static boolean addScore(String stuID, String courseID, double score) throws SQLException {
        String sql = "INSERT INTO Scores(stu_id, course_id, score) VALUES (?, ?, ?)";

        try (Connection connect = getConnection();
             PreparedStatement prestate = connect.prepareStatement(sql)) {

            prestate.setString(1, stuID);
            prestate.setString(2, courseID);
            prestate.setDouble(3, score);
            prestate.executeUpdate();
            return true;
        }
    }

    public static boolean deleteScore(int id) throws SQLException {
        String sql = "DELETE FROM Scores WHERE id = ?";

        try (Connection connect = getConnection();
             PreparedStatement prestate2 = connect.prepareStatement(sql)){


            prestate2.setInt(1, id);
            int affected = prestate2.executeUpdate();
            return affected > 0;
        }
    }
}

/*
=== AAAAAAHHHHHHHHHHHH!!!! ===
 */