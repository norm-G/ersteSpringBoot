package ng.uebungen.erste.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "Nutzer")
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id")
@Relation(value = "nutzer",collectionRelation = "nutzerListe")
public class Nutzer extends RepresentationModel<Nutzer>{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nutzer_name wird benoetigt")
	private String name;
	
	@NotEmpty(message = "Nutzer_password wird benoetigt")
	private String password;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "Nrollen",
			joinColumns = @JoinColumn(name="nutzer_id"),
			inverseJoinColumns = @JoinColumn(name="rollen_id"))
	private List<NutzerRolle> rollen = new ArrayList<NutzerRolle>();
		
	@JsonIgnore
	@OneToMany(mappedBy = "nutzer")
	private List<Einkauf> einkaeufe;
	
	

	
	protected Nutzer() {
		
	}

	public Nutzer( String name, String password) {
		this.name = name;
		this.password = password;
	}


	
	
	public Long getId() {
		return this.id;
	}


	public String getName() {
		return this.name;
	}
	
	public String getPassword() {
		return this.password;
	}


	public List<NutzerRolle> getRollen() {
		return this.rollen;
	}


	public List<Einkauf> getEinkaeufe() {
		return this.einkaeufe;
	}
	
	
	public void setNutzername(String name) {
		this.name = name;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public void addRolle(NutzerRolle rolle) {
		this.rollen.add(rolle);
	}
	
	public void setRollen(List<NutzerRolle> rollen) {
		this.rollen = rollen;
	}

	public void addEinkauf(Einkauf einkauf) {
		this.einkaeufe.add(einkauf);
	}
	
	public void setEinkaeufe(List<Einkauf> einkaeufe) {
		this.einkaeufe = einkaeufe;
	}
		

	@Override
	public String toString() {
		return "Nutzer [id=" + id + ", name=" + name + ", password=" + password + "]";
	}

	
	
	
	
}
