package Model;
import DAO.HolidayDAOImpl;
import Model.Employe.Poste;
import Model.Employe.Role;
import Model.Holiday.HolidayType;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HolidayModel {

    private HolidayDAOImpl dao;

    public HolidayModel(HolidayDAOImpl dao) {
        this.dao = dao;
    }

    public boolean ajouterHoliday(int id,int employeeId, Date startDate, Date endDate, HolidayType holidayType) {

        // Validate that start date is not after the end date
        if (!isValidDateRange(startDate, endDate)) {
            System.out.println("The start date must be before or equal to the end date.");
            return false;
        }

        // Calculate the number of holiday days
        long days = calculateHolidayDays(startDate, endDate);
        System.out.println(days + " day(s)");
 
        // Create and save the holiday
        Holiday newHoliday = new Holiday(id,employeeId, startDate, endDate, holidayType);
        int solde=dao.ajouter(newHoliday);
        long diff=(solde-days);
        if(diff<0) {
        	System.out.println(diff+"days");
        	return false;
        }

        return true;
    }
    private boolean isValidDateRange(Date startDate, Date endDate) {
        return !startDate.after(endDate);  // Ensure startDate is before or equal to endDate
    }

    private long calculateHolidayDays(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
    
    
    public List<Holiday> afficher() {
		return dao.afficher();
	}
    
    
    public boolean supprimer(int id) {
		if(id <= 0) {
			return false;
		}
		return dao.supprimer(id);
	}
    
    
    public boolean modifier(int id, int employeId, Date startDate, Date endDate, Holiday.HolidayType holidayType) {
        if (startDate == null || endDate == null) {
            System.out.println("Les dates ne peuvent pas être nulles!");
            return false;
        }

        if (startDate.after(endDate)) {
            System.out.println("La date de début ne peut pas être après la date de fin!");
            return false;
        }
        if (holidayType == null) {
            System.out.println("Le type de congé ne peut pas être nul!");
            return false;
        }
        Holiday updatedHoliday = new Holiday(id, employeId, startDate, endDate, holidayType);
        dao.modifier(id, updatedHoliday);
        return true;
    }

}


