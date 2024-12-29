package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Employe;
import Model.Employe.Poste;
import Model.Employe.Role;

public class EmployeDAOImpl implements GenericDAOI <Employe> ,DataImportExport<Employe>{

	@Override
	public int ajouter(Employe employe) {
		String sql= "INSERT INTO employes (nom , prenom , email , telephone , salaire , role_id , poste_id) VALUES "
				+ "(?,?,?,?,?,"
				+ "(SELECT id FROM roles WHERE nom = ?),"
				+ "(SELECT id FROM postes WHERE nom = ?))";
		try(PreparedStatement stmt = Connexion.getConnection().prepareStatement(sql)){
			stmt.setString(1, employe.getNom());
			stmt.setString(2, employe.getPrenom());
			stmt.setString(3, employe.getEmail());
			stmt.setString(4, employe.getTelephone());
			stmt.setDouble(5, employe.getSalaire());
	        stmt.setString(6, employe.getRole().name());
	        stmt.setString(7, employe.getPoste().name()); 
	        int isInserted=stmt.executeUpdate();
	        if(isInserted==0) {
	            System.out.println("Échec de l'insertion : aucun poste ou rôle correspondant trouvé.");
	            return 0;
	        }
	        
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 1;
		
	}
	@Override
	public List<Employe> afficher(){
		List <Employe> employes = new ArrayList<>();
		String sql="SELECT e.id,e.nom,e.prenom,e.email,e.telephone,e.salaire,r.nom AS role_nom,p.nom AS poste_nom FROM employes e JOIN roles r ON e.role_id=r.id JOIN postes p ON e.poste_id=p.id";
		try(PreparedStatement stmt =Connexion.getConnection().prepareStatement(sql)){
			ResultSet res=stmt.executeQuery();
			while(res.next()) {
				int id =res.getInt("id");
				String nom =res.getString("nom");
				String prenom = res.getString("prenom");
				String email = res.getString("email");
				String telephone =res.getString("telephone");
				Double salaire = res.getDouble("salaire");
				String role = res.getString("role_nom");
				String  poste = res.getString("poste_nom");
				Employe nvEmploye = new Employe(id,nom,prenom,email,telephone,salaire,Role.valueOf(role),Poste.valueOf(poste));
				employes.add(nvEmploye);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employes;
	}
	@Override 
	public boolean supprimer(int id) {
		String sql="DELETE FROM employes WHERE id=? ";
		try(PreparedStatement stmt =Connexion.getConnection().prepareStatement(sql)){
			stmt.setInt(1, id);
			int rowsAffected=stmt.executeUpdate();
			return rowsAffected>0;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public void modifier(int id, Employe employe) {
		 String sql = "UPDATE employes SET nom = ?, prenom = ?, email = ?, telephone = ?, salaire = ?, "
	               + "role_id = (SELECT id FROM roles WHERE nom = ?), "
	               + "poste_id = (SELECT id FROM postes WHERE nom = ?) "
	               + "WHERE id = ?";
		
		try(PreparedStatement stmt = Connexion.getConnection().prepareStatement(sql)){
			if(Connexion.getConnection()==null) {
				System.out.println("Echec de connecter à la bae de donnée.");
			}
			stmt.setString(1,employe.getNom());
			stmt.setString(2,employe.getPrenom());
			stmt.setString(3,employe.getEmail());
			stmt.setString(4,employe.getTelephone());
			stmt.setDouble(5,employe.getSalaire());
			stmt.setString(6,employe.getRole().name());
			stmt.setString(7,employe.getPoste().name());
			stmt.setInt(8,id);
			
			int rest=stmt.executeUpdate();
			if(rest>0) {
				System.out.println("Modification was succesful.");
			}else {
				System.out.println("aucune modification a effectuée");
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	@Override
	public void importData(String fileName) throws IOException {
		String query ="INSERT INTO employes (nom , prenom , email , telephone , salaire , role_id , poste_id) VALUES "
				+ "(?,?,?,?,?,"
				+ "(SELECT id FROM roles WHERE nom = ?),"
				+ "(SELECT id FROM postes WHERE nom = ?))";
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName));
				PreparedStatement stmt = Connexion.getConnection().prepareStatement(query)){
			
				String line = reader.readLine();
				while((line =reader.readLine()) !=null) {
					String[] data =line.split(",");
					if(data.length ==7) {
						stmt.setString(1, data[0].trim());
						stmt.setString(2, data[1].trim());
						stmt.setString(3, data[2].trim());
						stmt.setString(4, data[3].trim());
						stmt.setString(5, data[4].trim());
				        stmt.setString(6, data[5].trim());
				        stmt.setString(7, data[6].trim()); 
				        stmt.addBatch();
					}
				}
					stmt.executeBatch();
					System.out.println("Employees imported successfully!");
				}catch(IOException| SQLException e) {
					e.printStackTrace();
				}
		
	}
	
	
	@Override
    public void exportData(String fileName, List<Employe> data) throws IOException {
		System.out.println(""+fileName);
     try(BufferedWriter writer =new BufferedWriter(new FileWriter(fileName))){
         writer.write("nom, prenom, salaire, email, telephone, role, poste");
         writer.newLine();
         for(Employe employee : data){
             String Line = String.format("%s,%s,%.2f,%s,%s,%s,%s",
                     employee.getNom(),
                     employee.getPrenom(),
                     employee.getSalaire(),
                     employee.getEmail(),
                     employee.getTelephone(),
                     employee.getRole().name(),
                     employee.getPoste().name());
             writer.write(Line);
             writer.newLine();
         }
     }

    }
	
	
	
	
}
