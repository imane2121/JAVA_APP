package Model;

public class Employe {
	
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private double salaire;
	private Role role;
	private Poste poste;
	
	
	public Employe(int id,String nom, String prenom, String email, String telephone , double salaire , Role role , Poste poste ) {
		
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.salaire = salaire;
		this.role = role;
		this.poste = poste;
		
	}
	
	public Employe(Class<Integer> class1) {
	}

	public String getNom() {return nom;}
	public String getPrenom() {return prenom;}
	public String getEmail() {return email;}
	public String getTelephone() {return telephone;}
	public double getSalaire() {return salaire;}
	public int getEmployeId() {return id;}

	
	public enum Poste{
		INGENIEUR_ETUDE,
		TEAM_LEADER,
		PILOTE,
	}
	
	public enum Role{
		ADMIN,
		EMPLOYEE,
	}
	
	public Role getRole(){
		return role;
	}
	
	public Poste getPoste() {
		return poste;
	}
	
	public int getId() {
		return id;
	}
	
	
	


}
