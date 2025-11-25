package a1_2301040086;

import javax.swing.*;


public class StudentCourseManager {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Data.startData();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainApp mainWindow = new MainApp();
                mainWindow.setVisible(true);
            }
        });
    }
}