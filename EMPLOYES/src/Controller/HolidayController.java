package Controller;

import java.util.Date;
import java.util.List;

import DAO.EmployeDAOImpl;
import Model.HolidayModel;
import View.HolidayView;
import Model.Employe;
import Model.Holiday.HolidayType;

public class HolidayController {
	
	private HolidayView view;
	private HolidayModel model;
	
	public HolidayController(HolidayView view, HolidayModel model){
		this.model=model;
		this.view=view;
		view.ajouterButton.addActionListener(e -> ajouterHoliday());
		view.afficherButton.addActionListener(e->view.remplirTable(model.afficher()));
		view.supprimerButton.addActionListener(e->supprimer());
		view.modifierButton.addActionListener(e->modifierHoliday());
		loadEmployees();

	}
	//pour naviger les employes 
	  private void loadEmployees() {
	        EmployeDAOImpl employeDAO = new EmployeDAOImpl();
	        List<Employe> employees = employeDAO.afficher();
	        view.populateEmployeeList(employees);
	    }
	  
	public void ajouterHoliday() {
		int id=0;
		int Employeid=view.getSelectedEmployeeId();
		Date startDate=view.getStartDate(), endDate=view.getEndDate();
		HolidayType holidayType=view.getHolidayType();
		
		boolean ajoutResult=model.ajouterHoliday(id,Employeid, startDate, endDate, holidayType);
		
		if (ajoutResult) {
			view.afficherMessageSucces("Ajout reussi :)");
		}else {
			view.afficherMessageErreur("Solde Insuffisant!!");
		}
	}
	
	
	public void supprimer() {
		try {
		int id =view.getId();
		if(model.supprimer(id)) {
			view.afficherMessageSucces("Holiday supprimé avec succes .");
			view.remplirTable(model.afficher());
			
		}else {
			view.afficherMessageErreur("cannot delete Holiday");
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void modifierHoliday() {
	    try {
	        int id = view.getId();
	        int employeId = view.getSelectedEmployeeId();
	        Date startDate = view.getStartDate();
	        Date endDate = view.getEndDate();
	        HolidayType holidayType = view.getHolidayType();
	        
	        boolean modified = model.modifier(id, employeId, startDate, endDate, holidayType);

	        if (modified) {
	            view.afficherMessageSucces("Le congé a été modifié avec succès !");
	            view.remplirTable(model.afficher());
	        } else {
	            view.afficherMessageErreur("Échec de la modification du congé !");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        view.afficherMessageErreur("Une erreur inattendue s'est produite !");
	    }
	}

}
