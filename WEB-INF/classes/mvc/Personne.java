package mvc;

public class Personne {

	private int id;
	private String nom;
	private String prenom;
	private String sexe;
	private String tel;
	private String fonction;
	
	public Personne() {}
	
	public void setId(int id) { this.id = id; }
	
	public int getId() { return id; }
	
	public void setNom(String nom) { this.nom = nom; }
	
	public String getNom() { return nom; }
	
	public void setPrenom(String prenom) { this.prenom = prenom; }
	
	public String getPrenom() { return prenom; }
	
	public void setSexe(String sexe) { this.sexe = sexe; }
	
	public String getSexe() { return sexe; }
	
	public void setTel(String tel) { this.tel = tel; }
	
	public String getTel() { return tel; }
	
	public void setFonction(String fonction) { this.fonction = fonction; }

	public String getFonction() { return fonction; }
	
	public String getHtml() {
		return "<table border=1 cellspacing=0 cellpadding=0 style='float:left; margin: 5px'>"
			+ "<tr><td>Id</td><td>" + id + "</td></tr>"
			+ "<tr><td>Nom</td><td>" + nom + "</td></tr>"
			+ "<tr><td>Prenom</td><td>" + prenom + "</td></tr>"
			+ "<tr><td>Sexe</td><td>" + sexe + "</td></tr>"
			+ "<tr><td>Tel</td><td>" + tel + "</td></tr>"
			+ "<tr><td>Fonction</td><td>" + fonction + "</td></tr>"
			+ "</table>";
	}

}
