package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Holiday;
import Model.Holiday.HolidayType;

public class HolidayDAOImpl implements GenericDAOI<Holiday> {
    @Override
    public int ajouter(Holiday holiday) {

        ResultSet rs = null;
        int solde = 0;

        try {
        	 String queryDays = "select datediff(?, ?) as days_diff";
        	PreparedStatement stmt = Connexion.getConnection().prepareStatement(queryDays);
            stmt.setDate(1, new java.sql.Date(holiday.getEndDate().getTime()));
            stmt.setDate(2, new java.sql.Date(holiday.getStartDate().getTime()));
            rs = stmt.executeQuery();

            int daysDiff = 0;
            if (rs.next()) {
                daysDiff = rs.getInt("days_diff");
            }

            String querySolde = "select solde from employes where id=?";
            stmt = Connexion.getConnection().prepareStatement(querySolde);
            stmt.setInt(1, holiday.getEmployeeId());
            rs = stmt.executeQuery();

            
            if (rs.next()) {
                solde = rs.getInt("solde");
            }

            if (daysDiff <= solde) {
                String query = "insert into holiday (employe_id, start_date, end_date, conge_id) values (?, ?, ?,(select id from conges where nom=?))";
                
                stmt = Connexion.getConnection().prepareStatement(query);
                
                java.sql.Date sqlStartDate = new java.sql.Date(holiday.getStartDate().getTime());
                java.sql.Date sqlEndDate = new java.sql.Date(holiday.getEndDate().getTime());
                stmt.setInt(1, holiday.getEmployeeId());
                stmt.setDate(2, sqlStartDate);
                stmt.setDate(3, sqlEndDate);
                stmt.setString(4, holiday.getHolidayType().name());

                int rowAffected = stmt.executeUpdate();
                
                if (rowAffected > 0) {
                    String updateSoldeQuery = "update employes set solde=solde-? where id=?";
                    PreparedStatement updateStmt = Connexion.getConnection().prepareStatement(updateSoldeQuery);
                    updateStmt.setInt(1, daysDiff);
                    updateStmt.setInt(2, holiday.getEmployeeId());
                    updateStmt.executeUpdate();
                    
                    System.out.println("Insertion reussi :)");
                    
                } else {
                    System.out.println("Echec de l'insertion!!");
                    
                }
            } else {
                System.out.println("Solde insuffisant :(");
                return solde;
               
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solde;
    }

	@Override
	public List<Holiday> afficher() {
		List <Holiday> holidays = new ArrayList<>();
		String sql="SELECT h.id AS id,e.id AS employe_id, CONCAT(e.nom, ' ', e.prenom) AS nom_complet, h.start_date AS start_date, h.end_date AS end_date, c.nom AS type FROM Holiday h JOIN Employes e ON h.employe_id = e.id JOIN Conges c ON h.conge_id = c.id ";
		try(PreparedStatement stmt=Connexion.getConnection().prepareStatement(sql)){
			ResultSet res=stmt.executeQuery();
			while(res.next()) {
				int id=res.getInt("id");
				int employe_id=res.getInt("employe_id");
				String nom_complet =res.getString("nom_complet");
				String start_date=res.getString("start_date");
				String end_date=res.getString("end_date");
				String type=res.getString("type");	
				Date startDate = Date.valueOf(start_date);
	            Date endDate = Date.valueOf(end_date);
				Holiday newHoliday = new Holiday(id,employe_id,nom_complet,startDate,endDate,HolidayType.valueOf(type));
				holidays.add(newHoliday);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return holidays;
	}
	

	@Override
	public boolean supprimer(int id) {
		String sql="DELETE FROM holiday WHERE id=? ";
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
	public void modifier(int id, Holiday holiday) {
	    String sql = "UPDATE holiday SET start_date = ?, end_date = ?, conge_id = (SELECT id FROM conges WHERE nom = ?) WHERE id = ?";
	    try (PreparedStatement stmt = Connexion.getConnection().prepareStatement(sql)) {
	        stmt.setDate(1, new java.sql.Date(holiday.getStartDate().getTime()));
	        stmt.setDate(2, new java.sql.Date(holiday.getEndDate().getTime()));
	        stmt.setString(3, holiday.getHolidayType().name());
	        stmt.setInt(4, id);

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Holiday updated successfully!");
	        } else {
	            System.out.println("No holiday found with the specified ID.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
