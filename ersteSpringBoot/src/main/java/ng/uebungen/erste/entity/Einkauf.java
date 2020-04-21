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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="Einkauf")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Einkauf {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn
	private Nutzer nutzer;
	
	@ManyToMany
	@JoinTable(name="Einkaufswagen",
				joinColumns = @JoinColumn(name="einkauf_id"),
				inverseJoinColumns = @JoinColumn(name="artikel_id"))
	private List<Artikel> artikel;
	
	
	
	
	protected Einkauf() {
		
	}


	public Einkauf(Nutzer nutzer, List<Artikel> artikel) {
		super();
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



	@Override
	public String toString() {
		return "Einkauf [id=" + id + ", nutzer=" + nutzer + ", artikel=" + artikel + "]";
	}
	
	
	
	
	
	

}
