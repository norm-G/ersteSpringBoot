package ng.uebungen.erste.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="Artikel")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Artikel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private int preis;
	
	@ManyToMany(mappedBy = "artikel")
	private List<Einkauf> einkaeufe;

	
	protected Artikel() {
		
	}

	public Artikel(String name, int preis) {
		super();
		this.name = name;
		this.preis = preis;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPreis() {
		return preis;
	}

	public List<Einkauf> getEinkaeufe() {
		return einkaeufe;
	}

	@Override
	public String toString() {
		return "Artikel [id=" + id + ", name=" + name + ", preis=" + preis + "]";
	}
	
	
	
	
	
}
