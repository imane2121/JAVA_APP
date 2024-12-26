package Controller;

import Model.EmployeModel;

import java.sql.SQLException;
import java.util.List;

import Model.Employe.Poste;
import Model.Employe.Role;
import View.EmployeView;

public class EmployeController {
	private EmployeView view;
	private EmployeModel model;
	
	public EmployeController(EmployeView view, EmployeModel model) {
		this.model=model;
		this.view=view;
		
		this.view.ajouterButton.addActionListener(e-> ajouter());
		this.view.afficherButton.addActionListener(e -> view.remplirTable(model.afficher()));
		this.view.supprimerButton.addActionListener(e-> supprimer());
		this.view.modifierButton.addActionListener(e-> modifierEmploye());
	}


	public void modifierEmploye() {
		try {
			int id = view.getId()  ; 
	        String nom = view.getNom();
	        String prenom = view.getPrenom();
	        String email = view.getEmail();
	        String telephone = view.getTelephone();
	        double salaire = view.getSalaire();
	        Role role = view.getRole();
	        Poste poste = view.getPoste();
	        
	        boolean modified =model.modifier(id, nom, prenom, email, telephone, salaire, role, poste);
	        if(modified) {
	        	view.afficherMessageSucces("Employe a été modifié !");
	            view.remplirTable(model.afficher()); 
	        }else {
	        	view.afficherMessageErreur("Echec de modification");
	        }
	        
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}


	public void supprimer() {
		try {
		int id =view.getId();
		if(model.supprimer(id)) {
			view.afficherMessageSucces("Employe supprimé avec succes .");
			view.remplirTable(model.afficher());
			
		}else {
			view.afficherMessageErreur("cannot delete employe");
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}


	public void ajouter() {
		int id = 0;
		String nom = view.getNom();
		String prenom = view.getPrenom();
		String email = view.getEmail();
		String telephone = view.getTelephone();
		double salaire=view.getSalaire();
		Role role = view.getRole();
		Poste poste = view.getPoste();	
		boolean added=model.ajouter(id, nom, prenom, email, telephone, salaire, role, poste);
		if(added) {
			view.afficherMessageSucces("Employe ajoute avec succes");
		}else {
			view.afficherMessageErreur("Echec de l'ajout");
		}
	}

	
}
