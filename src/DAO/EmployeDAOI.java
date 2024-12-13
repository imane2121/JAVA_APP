package DAO;

import java.util.List;

import Model.Employe;

public interface EmployeDAOI {
	
	public void ajouterEmploye(Employe employe);
	public List<Employe> afficherEmployes();
	public void modifierEmploye(int id , Employe employe);
	public boolean supprimerEmploye(int id);



}
