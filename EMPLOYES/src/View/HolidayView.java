package View;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Model.Employe;
import Model.Holiday;
import Model.Holiday.HolidayType;

public class HolidayView extends JFrame {

    JPanel jp1 = new JPanel(), jp2 = new JPanel(), jp3 = new JPanel(), jp4 = new JPanel();

    private JLabel jlNom = new JLabel("Employe : "), jlType = new JLabel("Type : "), jlDateDebut = new JLabel("Date de debut : "), jlDateFin = new JLabel("Date de fin : ");
    private JComboBox<Employe> employeeComboBox = new JComboBox<>();
    private JComboBox<HolidayType> holidayType = new JComboBox<>(HolidayType.values());
    private Calendar calendar = Calendar.getInstance();

    private JSpinner startDateSpinner = new JSpinner(new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DAY_OF_MONTH));
    private JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy");

    private JSpinner endDateSpinner = new JSpinner(new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DAY_OF_MONTH));
    private JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy");

    private DefaultTableModel tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Nom complet", "Date de debut", "Date de fin", "Type" });
    private JTable jt = new JTable(tableModel);

    public JButton ajouterButton = new JButton("Ajouter"), modifierButton = new JButton("Modifier"), supprimerButton = new JButton("Supprimer"), afficherButton = new JButton("Afficher");

    public HolidayView() {
        setTitle("Gestion des conges");
        setLocationRelativeTo(null);
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(jp1);
        jp1.setLayout(new BorderLayout());
        jp1.add(jp2, BorderLayout.NORTH);
        jp2.setLayout(new GridLayout(4, 2));
        jp2.add(jlNom);
        jp2.add(employeeComboBox);
        jp2.add(jlType);
        jp2.add(holidayType);
        startDateSpinner.setEditor(startDateEditor);
        jp2.add(jlDateDebut);
        endDateSpinner.setEditor(endDateEditor);
        jp2.add(startDateSpinner);
        jp2.add(jlDateFin);
        jp2.add(endDateSpinner);

        jp1.add(jp3, BorderLayout.CENTER);
        jp3.setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(jt);
        jp3.add(jsp, BorderLayout.CENTER);

        jp1.add(jp4, BorderLayout.SOUTH);
        jp4.setLayout(new FlowLayout());
        jp4.add(ajouterButton);
        jp4.add(modifierButton);
        jp4.add(supprimerButton);
        jp4.add(afficherButton);

        jt.setFillsViewportHeight(true);
        jt.setPreferredScrollableViewportSize(new Dimension(500, 150));
        jt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = jt.getSelectedRow();
                if (selectedRow != -1) {
                    String tableFullName = tableModel.getValueAt(selectedRow, 1).toString(); // Name from table
                    for (int i = 0; i < employeeComboBox.getItemCount(); i++) {
                        Employe employee = employeeComboBox.getItemAt(i);
                        String comboFullName = employee.getNom() + " " + employee.getPrenom(); // Construct name
                        if (comboFullName.equals(tableFullName)) {
                            employeeComboBox.setSelectedItem(employee);
                            break;
                        }
                    }

                    String type = tableModel.getValueAt(selectedRow, 4).toString();
                    holidayType.setSelectedItem(HolidayType.valueOf(type));

                    // Parse dates from the table
                    Date startDate = (Date) tableModel.getValueAt(selectedRow, 2);
                    startDateSpinner.setValue(startDate);
                    Date endDate = (Date) tableModel.getValueAt(selectedRow, 3);
                    endDateSpinner.setValue(endDate);
                }
            }
        });


        employeeComboBox.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel();
            if (value != null) {
                label.setText(value.getPrenom() + " " + value.getNom());
            }
            return label;
        });
    }

    public void populateEmployeeList(List<Employe> employees) {
        employeeComboBox.removeAllItems();
        for (Employe employee : employees) {
            employeeComboBox.addItem(employee);
        }
    }

    public int getSelectedEmployeeId() {
        Employe selectedEmployee = (Employe) employeeComboBox.getSelectedItem();
        return selectedEmployee != null ? selectedEmployee.getId() : -1;
    }

    public HolidayType getHolidayType() {
        return (HolidayType) holidayType.getSelectedItem();
    }

    public Date getStartDate() {
        return (Date) startDateSpinner.getValue();
    }

    public Date getEndDate() {
        return (Date) endDateSpinner.getValue();
    }

    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherMessageSucces(String message) {
        JOptionPane.showMessageDialog(this, message, "Succes", JOptionPane.INFORMATION_MESSAGE);
    }

    public int getId() {
        int selectedRow = jt.getSelectedRow();
        if (selectedRow != -1) {
            return (int) jt.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    public void remplirTable(List<Holiday> holidays) {
        tableModel.setRowCount(0);
        for (Holiday e : holidays) {
            tableModel.addRow(new Object[] {
                e.getId(),
                e.getNom_complet(),
                e.getStartDate(),
                e.getEndDate(),
                e.getHolidayType()
            });
        }
    }
}
