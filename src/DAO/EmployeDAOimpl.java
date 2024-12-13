package DAO;

import DAO.EmployeDAOI;
import Model.Employe;
import Model.Employe.Poste;
import Model.Employe.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeDAOimpl implements EmployeDAOI {
	
	@Override 
	public void ajouterEmploye(Employe employe) {
		String sql = "INSERT INTO employes (nom , prenom , email , telephone , salaire , role_id , poste_id) VALUES "
				+ "(?,?,?,?,?,"
				+ "(SELECT id FROM roles WHERE nom = ?),"
				+ "(SELECT id FROM postes WHERE nom = ?))";
		
		try(PreparedStatement stmt = Connexion.getConnection().prepareStatement(sql)){
			stmt.setString(1 ,employe.getNom());
			stmt.setString(2, employe.getPrenom());
			stmt.setString(3, employe.getEmail());
			stmt.setString(4, employe.getTelephone());
			stmt.setDouble(5, employe.getSalaire());
	        stmt.setString(6, employe.getRole().name());
	        stmt.setString(7, employe.getPoste().name()); 

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected == 0) {
	            System.out.println("Échec de l'insertion : aucun poste ou rôle correspondant trouvé.");
	        }
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Employe> afficherEmployes() {
	    List<Employe> employes = new ArrayList<>();
	    String sql = "SELECT e.id, e.nom, e.prenom, e.email, e.telephone, e.salaire, "
	               + "r.nom AS role_nom, p.nom AS poste_nom FROM employes e "
	               + "JOIN roles r ON e.role_id = r.id "
	               + "JOIN postes p ON e.poste_id = p.id";

	    try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String nom = rs.getString("nom");
	            String prenom = rs.getString("prenom");
	            String email = rs.getString("email");
	            String telephone = rs.getString("telephone");
	            double salaire = rs.getDouble("salaire");
	            String roleNom = rs.getString("role_nom");
	            String posteNom = rs.getString("poste_nom");

	            Employe employe = new Employe(
	                id, nom, prenom, email, telephone, salaire,
	                Role.valueOf(roleNom), Poste.valueOf(posteNom)
	            );

	            employes.add(employe);
	        }

	    } catch (SQLException e) {
	        System.err.println("Erreur SQL : " + e.getMessage());
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.err.println("Erreur générale : " + e.getMessage());
	        e.printStackTrace();
	    }

	    return employes;
	}

	
	public void modifierEmploye(int id , Employe employe) {
		
		 String sql = "UPDATE employes SET nom = ?, prenom = ?, email = ?, telephone = ?, salaire = ?, "
	               + "role_id = (SELECT id FROM roles WHERE nom = ?), "
	               + "poste_id = (SELECT id FROM postes WHERE nom = ?) "
	               + "WHERE id = ?";

		try(PreparedStatement stmt = Connexion.getConnection().prepareStatement(sql)){
			
			if (Connexion.getConnection() == null) {
			    System.out.println("Échec de la connexion à la base de données.");
			}
			
			stmt.setString(1, employe.getNom());
			stmt.setString(2, employe.getPrenom());
			stmt.setString(3, employe.getEmail());
			stmt.setString(4, employe.getTelephone());
			stmt.setDouble(5, employe.getSalaire());
			stmt.setString(6, employe.getRole().name());
			stmt.setString(7, employe.getPoste().name());
			stmt.setInt(8, id);
			
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
			    System.out.println("Employé modifié avec succès.");
			} else {
			    System.out.println("Aucune modification effectuée.");
			}
			
			System.out.println("ID : " + id);
			System.out.println("Nom : " + employe.getNom());
			System.out.println("Prénom : " + employe.getPrenom());
			System.out.println("Email : " + employe.getEmail());
			System.out.println("Téléphone : " + employe.getTelephone());
			System.out.println("Salaire : " + employe.getSalaire());
			System.out.println("Role : " + employe.getRole().name());
			System.out.println("Poste : " + employe.getPoste().name());

			
		}catch(SQLException e) {
		    System.out.println("Erreur SQL : " + e.getMessage());

			e.printStackTrace();
		}
	}
	
	@Override 
	public boolean supprimerEmploye(int id) {
		String sql = "DELETE FROM employes WHERE id = ?";
		
		try(PreparedStatement stmt = Connexion.getConnection().prepareStatement(sql)){
			stmt.setInt(1, id);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	
}
