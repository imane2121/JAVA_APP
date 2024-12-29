package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.DefaultTableModel;
import Model.Employe;
import Model.Employe.Poste;
import Model.Employe.Role;
import View.EmployeView;
import DAO.GenericDAOI;
import java.util.List;

public class EmployeView extends JFrame {
	
	private JPanel General = new JPanel();
	private JPanel pan1 = new JPanel();
	private JPanel pan3 = new JPanel();
	private JPanel pan4 = new JPanel();
	
	private JComboBox<Role> roleCombox;
	private JComboBox<Poste> posteCombox;

	
	
	private JTextField nomField = new JTextField();
	private JTextField prenomField = new JTextField();
	private JTextField emailField = new JTextField();
	private JTextField telephoneField = new JTextField();
	private JTextField salaireField = new JTextField();
	
	public JButton ajouterButton = new JButton("Ajouter");
	public JButton modifierButton = new JButton("Modifier");
	public JButton supprimerButton = new JButton("Supprimer");
	public JButton afficherButton = new JButton("Afficher");
	public JButton importerButton = new JButton("Importer");
	public JButton ExporterButton = new JButton("Exporter");

	
	private DefaultTableModel tableModel = new DefaultTableModel (
			new Object [][] {},new String[] {"ID","Nom" , "Prenom" , "Email" , "Telephone" , "Salaire"});
	
	private JTable Tableau = new JTable(tableModel);
	
	
	
	public EmployeView() {
		
		
		setTitle("Gestion des employes");
		setSize(600,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
        roleCombox = new JComboBox<>(Role.values());
        posteCombox = new JComboBox<>(Poste.values());
		
		add(General);
		General.setLayout(new BorderLayout());
		General.add(pan1 ,BorderLayout.NORTH);
		pan1.setLayout(new GridLayout(7,2));
		
		pan1.add(new JLabel("Nom:"));
		pan1.add(nomField);
		pan1.add(new JLabel("Prenom:"));
		pan1.add(prenomField);
		pan1.add(new JLabel("Email:"));
		pan1.add(emailField);
		pan1.add(new JLabel("Telephone:"));
		pan1.add(telephoneField);
		pan1.add(new JLabel("salaire:"));
		pan1.add(salaireField);
	    pan1.add(new JLabel("Role:"));
		pan1.add(roleCombox);
		pan1.add(new JLabel("Poste"));
		pan1.add(posteCombox);
		
		General.add(pan3 , BorderLayout.CENTER);
		pan3.setLayout(new BorderLayout());
		Tableau.setFillsViewportHeight(true);
        Tableau.setPreferredScrollableViewportSize(new Dimension(500, 150));
        Tableau.addMouseListener(new MouseAdapter() {
        	
        	public void mouseClicked(MouseEvent e) {
        		int selectedRow = Tableau.getSelectedRow();
        		if(selectedRow != -1) {
        			nomField.setText(tableModel.getValueAt(selectedRow, 1).toString());
        		    prenomField.setText(tableModel.getValueAt(selectedRow, 2).toString());
        		    emailField.setText(tableModel.getValueAt(selectedRow, 3).toString());
        		    telephoneField.setText(tableModel.getValueAt(selectedRow, 4).toString());
        		    salaireField.setText(tableModel.getValueAt(selectedRow, 5).toString());
         		}
        	}
        	
        }) ;
        	
        	
        
        pan3.add(new JScrollPane(Tableau), BorderLayout.CENTER);
        
        General.add(pan4 , BorderLayout.SOUTH);
        pan4.setLayout(new FlowLayout());
        pan4.add(ajouterButton);
        pan4.add(modifierButton);
        pan4.add(supprimerButton);
        pan4.add(afficherButton);
        pan4.add(importerButton);
        pan4.add(ExporterButton);


		
	}
	
	public String getNom() {return nomField.getText();}
	public String getPrenom() {return prenomField.getText();}
	public String getEmail() {return emailField.getText();}
	public String getTelephone() {return telephoneField.getText();}
	public double getSalaire() {return Double.parseDouble(salaireField.getText());}
	public Role getRole() {return (Role) roleCombox.getSelectedItem();}
	public Poste getPoste() {return (Poste) posteCombox.getSelectedItem();}
	
	public int getId() {
	    // Si vous utilisez une JTable, par exemple, vous pouvez obtenir l'ID de l'employé sélectionné ainsi :
	    int selectedRow = Tableau.getSelectedRow();  // Remplacer 'tableEmployes' par 'Tableau' (nom correct de votre JTable)
	    if (selectedRow != -1) {
	        // Supposons que l'ID est dans la première colonne de la table
	        return (int) Tableau.getValueAt(selectedRow, 0);  // La colonne 0 contient l'ID
	    }
	    return -1;  // Si aucun employé n'est sélectionné
	}

	
	
	
	public void remplirTable(List<Employe> employes) {
		
		tableModel.setRowCount(0);
		
		for(Employe e : employes) {
			tableModel.addRow(new Object[] {
					
					e.getId(),
					e.getNom(),
					e.getPrenom(),
					e.getEmail(),
					e.getTelephone(),
					e.getSalaire(),
					e.getRole(),
					e.getPoste()
					
			});
		}
	}
	
	
	public void afficherMessageErreur(String message) {
		JOptionPane.showMessageDialog(this, message , "Erreur", JOptionPane.ERROR_MESSAGE);
	}
	
	public void afficherMessageSucces(String message) {
		JOptionPane.showMessageDialog(this, message , "Succes" ,JOptionPane.INFORMATION_MESSAGE);
	}


	

}