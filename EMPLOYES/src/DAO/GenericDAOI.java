package DAO;

import java.util.List;

import Model.Employe;

public interface GenericDAOI<T> {
		public int ajouter(T obj);
		public List<T> afficher();
		public boolean supprimer(int id);
		public void modifier(int id ,T obj);
	
}
