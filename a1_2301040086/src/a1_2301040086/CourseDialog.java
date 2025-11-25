package a1_2301040086;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CourseDialog extends JDialog {
    private JTextField moduleCodeField;
    private JTextField courseNameField;

    public CourseDialog(JFrame parent) {
        super(parent, "Add Course", true);
        this.setSize(400, 200);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Course ID:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        moduleCodeField = new JTextField(20);
        formPanel.add(moduleCodeField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Course Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        courseNameField = new JTextField(20);
        formPanel.add(courseNameField, gbc);

        add(formPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
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

    private void addCourse() {
        String moduleCode = moduleCodeField.getText().trim();
        String courseName = courseNameField.getText().trim();


        if (moduleCode.isEmpty() || courseName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields are required.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (moduleCode.length() != 9) {
            JOptionPane.showMessageDialog(this,
                    "Course ID must be exactly 9 characters.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Data.addCourse(moduleCode, courseName);
            JOptionPane.showMessageDialog(this, "Course added !");
            dispose();
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                JOptionPane.showMessageDialog(this,
                        "Course ID already exists.", "Duplicate ID",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error adding course: " + e.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}