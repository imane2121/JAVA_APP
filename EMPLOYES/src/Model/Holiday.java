package Model;

import java.util.Date;

public class Holiday {
	
	private int id;
	private Date startDate, endDate;
	private HolidayType holidayType;
	private int employeeId;
	private String nom_complet;
	
	public Holiday(int id,int employeeId, Date startDate, Date endDate, HolidayType holidayType){
		this.id=id;
		this.startDate=startDate;
		this.endDate=endDate;
		this.holidayType=holidayType;
		this.employeeId=employeeId;
	}
	
	public Holiday(int id,int employeeId,String nom_complet,Date startDate, Date endDate, HolidayType holidayType) {
		this.id=id;
		this.startDate=startDate;
		this.nom_complet=nom_complet;
		this.endDate=endDate;
		this.holidayType=holidayType;
		this.employeeId=employeeId;
		
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setHolidayType(HolidayType holidayType) {
		this.holidayType = holidayType;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getNom_complet() {
		return nom_complet;
	}

	public void setNom_complet(String nom_complet) {
		this.nom_complet = nom_complet;
	}

	public int getId() {return id;}
	public Date getStartDate() {return startDate;}
	public Date getEndDate() {return endDate;}
	public HolidayType getHolidayType() {return holidayType;}
	public int getEmployeeId() {return employeeId;}
	
	
	
	public enum HolidayType {
		CONGE_PAYEE, 
		CONGE_NON_PAYEE,
		CONGE_MALADIE
	}






	

}
