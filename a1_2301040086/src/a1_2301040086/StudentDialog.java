package a1_2301040086;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class StudentDialog extends JDialog {
    private JTextField studentIdField;
    private JTextField fullNameField;

    public StudentDialog(JFrame parent) {
        super(parent, "Add Student", true);
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
        formPanel.add(new JLabel("Student ID:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        studentIdField = new JTextField(20);
        formPanel.add(studentIdField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Full Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        fullNameField = new JTextField(20);
        formPanel.add(fullNameField, gbc);

        add(formPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
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

    private void addStudent() {
        String studentId = studentIdField.getText().trim();
        String fullName = fullNameField.getText().trim();


        if (studentId.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields are required.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (studentId.length() != 10) {
            JOptionPane.showMessageDialog(this,
                    "Student ID must be exactly 10 digits.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!studentId.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                    "Student ID must contain only digits.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Data.addStudents(studentId, fullName);
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            dispose();
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                JOptionPane.showMessageDialog(this,
                        "Student ID already exists.", "Duplicate ID",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error adding student: " + e.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}