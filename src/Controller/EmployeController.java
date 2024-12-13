package Controller;

import Model.Employe;
import Model.Employe.Poste;
import Model.Employe.Role;
import Model.EmployeModel;
import View.EmployeView;

import java.sql.SQLException;
import java.util.*;

import DAO.EmployeDAOI;
import DAO.EmployeDAOimpl;


public class EmployeController {
	
	private EmployeView view;
	private EmployeModel model;
	
	public EmployeController(EmployeView view , EmployeModel model) {
		this.view = view ;
		this.model = model;
		
		this.view.ajouterButton.addActionListener(e -> ajouterEmploye());
		this.view.afficherButton.addActionListener(e -> view.remplirTable(model.afficherEmployes()));
		this.view.modifierButton.addActionListener(e -> modifierEmploye());
		this.view.supprimerButton.addActionListener(e -> supprimerEmploye());

	}
	

	public void ajouterEmploye() {
		int id = 0;
		String nom = view.getNom();
		String prenom = view.getPrenom();
		String email = view.getEmail();
		String telephone = view.getTelephone();
		double salaire;
		Role role = view.getRole();
		Poste poste = view.getPoste();
		
		
		
		try {
			
			salaire = view.getSalaire();
			
		}catch(NumberFormatException e) {
			
			view.afficherMessageErreur("Salaire doit etre positif");
			return;
		}
		
		boolean ajoutReussi = model.ajouterEmploye(id,nom, prenom, email, telephone, salaire,role,poste);
		
		if(ajoutReussi) {
			view.afficherMessageSucces("Employe ajoute avec succes");
		}else {
			view.afficherMessageErreur("Echec de l'ajout");
		}
		
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
	        
	        boolean modificationReussie = model.modifierEmploye(id, nom, prenom, email, telephone, salaire, role, poste);
	        if (modificationReussie) {
	            view.afficherMessageSucces("Employé modifié avec succès.");
	            view.remplirTable(model.afficherEmployes()); 
	        } else {
	            view.afficherMessageErreur("Échec de la modification.");
	        }
			
		}catch(NumberFormatException e) {
	        view.afficherMessageErreur("ID ou salaire invalide !");
		}
		
		
	}
	
	public void supprimerEmploye() {
		try {
			int id = view.getId();

			
			if(model.supprimerEmploye(id)) {
				view.afficherMessageSucces("Employé supprimé avec succès.");
				view.remplirTable(model.afficherEmployes());
			}else {
				view.afficherMessageErreur("Échec de la suppression. Vérifiez l'ID.");
			}
			
		}catch(NumberFormatException e) {
	        view.afficherMessageErreur("ID invalide !");

		}
	}
	
	
	

}
