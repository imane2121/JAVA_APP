package Model;

import java.io.File;
import java.io.IOException;
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
	
	private boolean checkFileExists(File file) {
		if(!file.exists()) {
			throw new IllegalArgumentException("Le fichier n'existe pas:"+file.getPath());
		}return true;
	}
	
	private boolean checkIsFile(File file) {
		if(!file.isFile()) {
			throw new IllegalArgumentException("Le chemin spécifié n'est pas un fichier :"+file.getPath());
		}
		return true;
	}
	
	private boolean checkIsReadable(File file) {
		if(!file.canRead()) {
			throw new IllegalArgumentException("Le fichier n'est pas lisible :"+file.getPath());

		}
		return true;
	}
	private boolean checkIsWritable(File file) {
	    if (!file.canWrite()) {
	        throw new IllegalArgumentException("Le fichier n'est pas inscriptible :" + file.getPath());
	    }
	    return true;
	}

	public void importData(String FileName) throws IOException {
        File file = new File(FileName);
        checkFileExists(file);
        checkIsFile(file);
        checkIsReadable(file);
        dao.importData(FileName);
    }
	public void exportData(String FileName , List<Employe> data) throws IOException {
		File file = new File(FileName);
        dao.exportData(FileName,data);
	}
}
