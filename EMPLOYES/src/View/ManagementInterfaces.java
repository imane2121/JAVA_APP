package View;

import javax.swing.*;
import java.awt.*;

public class ManagementInterfaces extends JFrame {
    private JTabbedPane tabbedPane = new JTabbedPane();

    public ManagementInterfaces(EmployeView employeeView, HolidayView holidayView) {
        setTitle("Gestion des Employés et Congés");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add views to the tabbed pane
        tabbedPane.addTab("Gestion des Employés", employeeView.getContentPane());
        tabbedPane.addTab("Gestion des Congés", holidayView.getContentPane());

        // Add tabbed pane to the main frame
        add(tabbedPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ManagementInterfaces(new EmployeView(), new HolidayView());
    }
}
