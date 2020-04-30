package ng.uebungen.erste.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;




@Entity
@Table(name = "Rollen")
//@JsonIdentityInfo( 
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id")
//@JsonRootName(value = "rolle")
@Relation(value = "rolle",collectionRelation = "rollen")
public class NutzerRolle extends RepresentationModel<NutzerRolle>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String bezeichnung;
	
	@ManyToMany(mappedBy = "rollen")
	@JsonIgnore
	List<Nutzer> nutzer;
	
	
	protected NutzerRolle() {
		
	}
		
	public NutzerRolle(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}


	public Long getId() {
		return id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public List<Nutzer> getNutzer() {
		return nutzer;
	}
	
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public void setNutzer(List<Nutzer> nutzer) {
		this.nutzer = nutzer;
	}

//	@Override
//	public String toString() {
//		return "NutzerRolle [Id=" + id + ", bezeichnung=" + bezeichnung + ", nutzer=" + nutzer + "]";
//	}
//	
	
	
	
	

}
