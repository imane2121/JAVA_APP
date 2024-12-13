package Model;

import java.util.List;

import DAO.EmployeDAOimpl;
import Model.Employe.Poste;
import Model.Employe.Role;

public class EmployeModel {

	private EmployeDAOimpl dao;
	
	public EmployeModel(EmployeDAOimpl dao) {
		this.dao = dao;
	}
	
	public boolean ajouterEmploye( int id,String nom , String prenom , String email
			, String telephone , double salaire , Role role , Poste poste ){
		
		
		if(email == null || !email.contains("@")) {
			System.out.println("Erreur: Email doit contenir @");
			return false;
		}
		
		if(salaire <= 0) {
			System.out.println("Erreur: Salaire invalide");
			return false;
		}
		
		if (!telephone.matches("\\d{10}")) { 
            System.out.println("Numéro de téléphone invalide : " + telephone);
            return false;
        }
		
		Employe nouveauEmploye = new Employe(id,nom,prenom,email,telephone,salaire,role,poste);
		
		
		
		dao.ajouterEmploye(nouveauEmploye);
		
		
		return true;
	
	}
	
	public List<Employe> afficherEmployes(){
		return dao.afficherEmployes();

	}
	
	public boolean modifierEmploye(int id , String nom , String prenom , String email , String telephone , double salaire , Role role , Poste poste) {
		
		if(email == null || !email.contains("@")) {
			System.out.println("Erreur: Email doit contenir @");
			return false;
		}
		
		if(salaire <= 0) {
			System.out.println("Erreur: Salaire invalide");
			return false;
		}
		
		if (!telephone.matches("\\d{10}")) { 
            System.out.println("Numéro de téléphone invalide : " + telephone);
            return false;
        }
		
		Employe employe = new Employe(id,nom,prenom,email,telephone,salaire,role,poste);
		dao.modifierEmploye(id, employe);
		return true;
		
	}
	
	public boolean supprimerEmploye(int id) {
		if(id <= 0) {
			System.out.println("Erreur: ID invalide");
			return false;
		}
		
		return dao.supprimerEmploye(id);
	}
}
