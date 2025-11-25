package a1_2301040086;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ScoreDialog extends JDialog {
    private JComboBox<String> studentIdCombo;
    private JComboBox<String> moduleCodeCombo;
    private JTextField scoreField;

    public ScoreDialog(JFrame parent) {
        super(parent, "Add Score", true);
        this.setSize(400, 250);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);

        initComponents();
        loadComboBoxData();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Student ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Student ID:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        studentIdCombo = new JComboBox<String>();
        formPanel.add(studentIdCombo, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Course ID:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        moduleCodeCombo = new JComboBox<String>();
        formPanel.add(moduleCodeCombo, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Score (0-10):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        scoreField = new JTextField(20);
        formPanel.add(scoreField, gbc);

        add(formPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addScore();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadComboBoxData() {
        List<Student> students = Data.getStudents();
        for (Student s : students) {
            studentIdCombo.addItem(s.getStudentID());
        }

        List<Course> courses = Data.getCourse();
        for (Course c : courses) {
            moduleCodeCombo.addItem(c.getCourseID());
        }
    }

    private void addScore() {
        String studentId = (String) studentIdCombo.getSelectedItem();
        String moduleCode = (String) moduleCodeCombo.getSelectedItem();
        String scoreText = scoreField.getText().trim();


        if (studentId == null || moduleCode == null) {
            JOptionPane.showMessageDialog(this,
                    "Please ensure students and courses exist in the system.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (scoreText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Score field is required.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double score;
        try {
            score = Double.parseDouble(scoreText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Score must be a valid number.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (score < 0 || score > 10) {
            JOptionPane.showMessageDialog(this,
                    "Score must be between 0 and 10.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Data.addScore(studentId, moduleCode, score);
            JOptionPane.showMessageDialog(this, "Score added successfully!");
            dispose();
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                JOptionPane.showMessageDialog(this,
                        "Score already exists for this student and course.",
                        "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
            } else if (e.getMessage().contains("FOREIGN KEY constraint failed")) {
                JOptionPane.showMessageDialog(this,
                        "Invalid student ID or module code.", "Foreign Key Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error adding score: " + e.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}