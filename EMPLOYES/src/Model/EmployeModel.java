package Model;

import java.util.List;

import DAO.EmployeDAOImpl;
import Model.Employe.Poste;
import Model.Employe.Role;

public class EmployeModel {
	private EmployeDAOImpl dao;
	public EmployeModel(EmployeDAOImpl dao) {
		this.dao=dao;
	}
	public boolean ajouter(int id,String nom , String prenom , String email
			, String telephone , double salaire , Role role , Poste poste) {
		if(email==null || !email.contains("@")) {
			System.out.println("Email Invalide!!");
			return false;
		}
		if(salaire<0) {
			System.out.println("Salaire Invalide");
			return false;
		}
		Employe nvEmploye = new Employe(id,nom ,prenom ,email
			,telephone ,salaire ,role ,poste);
		dao.ajouter(nvEmploye);
		return true;
	}

	public List<Employe> afficher() {
		return dao.afficher();
	}
	public boolean supprimer(int id) {
		if(id <= 0) {
			System.out.println("Erreur: ID invalide");
			return false;
		}
		return dao.supprimer(id);
	}
	
	
	public boolean modifier(int id,String nom , String prenom , String email
			, String telephone , double salaire , Role role , Poste poste) {
		if(email==null || !email.contains("@")) {
			System.out.println("Email Invalide!!");
			return false;
		}
		if(salaire<0) {
			System.out.println("Salaire Invalide");
			return false;
		}
		Employe nvvEmploye =new Employe(id,nom ,prenom ,email
			,telephone ,salaire ,role ,poste);
		dao.modifier(id, nvvEmploye);
		return true;
	}
}
