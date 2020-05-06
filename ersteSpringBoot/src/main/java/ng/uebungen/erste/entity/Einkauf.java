package ng.uebungen.erste.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="Einkauf")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Relation(value = "einkauf",collectionRelation = "einkaufListe")
public class Einkauf extends RepresentationModel<Einkauf>{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private Nutzer nutzer;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name="Einkaufswagen",
				joinColumns = @JoinColumn(name="einkauf_id"),
				inverseJoinColumns = @JoinColumn(name="artikel_id"))
	private List<Artikel> artikel;
	
	
	
	
	protected Einkauf() {
		
	}

	public Einkauf(Nutzer nutzer) {
		this.nutzer = nutzer;
	}
		
	public Einkauf(Nutzer nutzer, List<Artikel> artikel) {
		this.nutzer = nutzer;
		this.artikel = artikel;
	}



	public Long getId() {
		return id;
	}



	public Nutzer getNutzer() {
		return nutzer;
	}



	public List<Artikel> getArtikel() {
		return artikel;
	}
	
	
	public void addArtikel(Artikel artikel) {
		this.artikel.add(artikel);
	}
	
	public void removeArtiekl(Artikel artikel) {
		this.artikel.remove(artikel);
	}
	
	public void setArtikel(List<Artikel> artikel) {
		this.artikel = artikel;
	}


	@Override
	public String toString() {
		return "Einkauf [id=" + id + ", nutzer=" + nutzer + ", artikel=" + artikel + "]";
	}
	
	
	
	
	
	

}
