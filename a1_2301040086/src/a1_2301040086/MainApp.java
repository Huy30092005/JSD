package a1_2301040086;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {
    private JTable dataTable;
    private DefaultTableModel table;
    private JRadioButton studentsRadio;
    private JRadioButton coursesRadio;
    private JRadioButton scoresRadio;
    private ButtonGroup radioGroup;

    private static final int MODE_STUDENTS = 0;
    private static final int MODE_COURSES = 1;
    private static final int MODE_SCORES = 2;

    private int currentMode = MODE_STUDENTS;

    public MainApp() {
        setTitle("F2025 JSD Assignment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        loadStudentsData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        studentsRadio = new JRadioButton("Students", true);
        coursesRadio = new JRadioButton("Courses");
        scoresRadio = new JRadioButton("Scores");

        radioGroup = new ButtonGroup();
        radioGroup.add(studentsRadio);
        radioGroup.add(coursesRadio);
        radioGroup.add(scoresRadio);

        studentsRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToStudentsView();
            }
        });

        coursesRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToCoursesView();
            }
        });

        scoresRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToScoresView();
            }
        });

        topPanel.add(studentsRadio);
        topPanel.add(coursesRadio);
        topPanel.add(scoresRadio);

        add(topPanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 10));

        table = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        dataTable = new JTable(table);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.setRowHeight(30);
        dataTable.getTableHeader().setReorderingAllowed(false);
        dataTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dataTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);


        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 15, 15));

        JButton addStudentBtn = createButton("Add Student");
        JButton addCourseBtn = createButton("Add Course");
        JButton addScoreBtn = createButton("Add Score");

        addStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddStudentDialog();
            }
        });

        addCourseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddCourseDialog();
            }
        });

        addScoreBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddScoreDialog();
            }
        });

        rightPanel.add(addStudentBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(addCourseBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(addScoreBtn);
        rightPanel.add(Box.createVerticalGlue());

        add(rightPanel, BorderLayout.EAST);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(120, 35));
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    private void switchToStudentsView() {
        currentMode = MODE_STUDENTS;
        loadStudentsData();
    }

    private void switchToCoursesView() {
        currentMode = MODE_COURSES;
        loadCoursesData();
    }

    private void switchToScoresView() {
        currentMode = MODE_SCORES;
        loadScoresData();
    }

    private void loadStudentsData() {
        table.setRowCount(0);
        table.setColumnCount(0);
        table.addColumn("ID");
        table.addColumn("Name");
        table.addColumn("Action");

        java.util.List<Student> students = Data.getStudents();
        for (final Student s : students) {
            Object[] rowData = new Object[3];
            rowData[0] = s.getStudentID();
            rowData[1] = s.getStudentName();
            rowData[2] = "Delete";
            table.addRow(rowData);
        }


        dataTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        dataTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), this, MODE_STUDENTS));


        dataTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        dataTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        dataTable.getColumnModel().getColumn(2).setPreferredWidth(100);
    }

    private void loadCoursesData() {
        table.setRowCount(0);
        table.setColumnCount(0);
        table.addColumn("ID");
        table.addColumn("Name");
        table.addColumn("Action");

        java.util.List<Course> courses = Data.getCourse();
        for (Course c : courses) {
            Object[] rowData = new Object[3];
            rowData[0] = c.getCourseID();
            rowData[1] = c.getCourseName();
            rowData[2] = "Delete";
            table.addRow(rowData);
        }

        dataTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        dataTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), this, MODE_COURSES));

        dataTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        dataTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        dataTable.getColumnModel().getColumn(2).setPreferredWidth(100);
    }

    private void loadScoresData() {
        table.setRowCount(0);
        table.setColumnCount(0);
        table.addColumn("Student ID");
        table.addColumn("Course ID");
        table.addColumn("Score");
        table.addColumn("Action");

        java.util.List<Score> scores = Data.getScores();
        for (Score s : scores) {
            Object[] rowData = new Object[4];
            rowData[0] = s.getStuID();
            rowData[1] = s.getCourseID();
            rowData[2] = String.format("%.1f", s.getScore());
            rowData[3] = "Delete";
            table.addRow(rowData);
        }

        dataTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        dataTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), this, MODE_SCORES));

        dataTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        dataTable.getColumnModel().getColumn(1).setPreferredWidth(130);
        dataTable.getColumnModel().getColumn(2).setPreferredWidth(130);
        dataTable.getColumnModel().getColumn(3).setPreferredWidth(100);
    }

    private void openAddStudentDialog() {
        StudentDialog dialog = new StudentDialog(this);
        dialog.setVisible(true);
        if (currentMode == MODE_STUDENTS) {
            loadStudentsData();
        }
    }

    private void openAddCourseDialog() {
        CourseDialog dialog = new CourseDialog(this);
        dialog.setVisible(true);
        if (currentMode == MODE_COURSES) {
            loadCoursesData();
        }
    }

    private void openAddScoreDialog() {
        ScoreDialog dialog = new ScoreDialog(this);
        dialog.setVisible(true);
        if (currentMode == MODE_SCORES) {
            loadScoresData();
        }
    }

    public void deleteStudent(String studentId) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete student " + studentId + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Data.deleteStudent(studentId);
                loadStudentsData();
                JOptionPane.showMessageDialog(this, "Student deleted successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deleteCourse(String courseID) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete course " + courseID + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Data.deleteCourse(courseID);
                loadCoursesData();
                JOptionPane.showMessageDialog(this, "Course deleted successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting course: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deleteScore(String studentId, String courseID) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this score?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {

                java.util.List<Score> scores = Data.getScores();
                for (Score s : scores) {
                    if (s.getStuID().equals(studentId) && s.getCourseID().equals(courseID)) {
                        Data.deleteScore(s.getID());
                        break;
                    }
                }
                loadScoresData();
                JOptionPane.showMessageDialog(this, "Score deleted successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting score: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
