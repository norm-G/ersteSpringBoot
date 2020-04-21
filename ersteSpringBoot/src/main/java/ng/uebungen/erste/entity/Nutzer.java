package ng.uebungen.erste.entity;

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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name = "Nutzer")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Nutzer {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nutzer_name wird benoetigt")
	private String nutzername;
	
	@NotEmpty(message = "Nutzer_password wird benoetigt")
	private String password;
	
	@ManyToMany
	@JoinTable(
			name = "Nutzerrollen",
			joinColumns = @JoinColumn(name="nutzer_id"),
			inverseJoinColumns = @JoinColumn(name="rollen_id"))
	private List<NutzerRolle> rolle;
		
	
	@OneToMany(mappedBy = "nutzer")
	private List<Einkauf> einkaeufe;
	
	
	
	protected Nutzer() {
		
	}


	public Nutzer( String nutzername, String password, List<NutzerRolle> rollen) {
		super();
		this.nutzername = nutzername;
		this.password = password;
		this.rolle = rollen;
	}


	public Long getId() {
		return id;
	}


	public String getName() {
		return nutzername;
	}
	
	public String getPassword() {
		return password;
	}


	public List<NutzerRolle> getRollen() {
		return rolle;
	}


	public List<Einkauf> getEinkaeufe() {
		return einkaeufe;
	}


	@Override
	public String toString() {
		return "Nutzer [id=" + id + ", nutzername=" + nutzername + ", password=" + password + "]";
	}
	
	
	
}
