// Handle Delete Cell
package a1_2301040086;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;
    private MainApp mainWindow;
    private int mode;

    public ButtonEditor(JCheckBox checkBox, MainApp mainWindow, int mode) {
        super(checkBox);
        this.mainWindow = mainWindow;
        this.mode = mode;

        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.table = table;
        label = (value == null) ? "Delete" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = table.getSelectedRow();
            if (row >= 0) {
                if (mode == 0) {
                    String stuID = table.getValueAt(row, 0).toString();
                    mainWindow.deleteStudent(stuID);
                } else if (mode == 1) {
                    String courseID = table.getValueAt(row, 0).toString();
                    mainWindow.deleteCourse(courseID);
                } else if (mode == 2) {
                    String studentId = table.getValueAt(row, 0).toString();
                    String courseID = table.getValueAt(row, 1).toString();
                    mainWindow.deleteScore(studentId, courseID);
                }
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
