package ng.uebungen.erste;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ng.uebungen.erste.entity.Artikel;
import ng.uebungen.erste.entity.Einkauf;
import ng.uebungen.erste.entity.Nutzer;
import ng.uebungen.erste.entity.NutzerRolle;
import ng.uebungen.erste.repository.ArtikelRepository;
import ng.uebungen.erste.repository.EinkaufRepository;
import ng.uebungen.erste.repository.NutzerRepository;
import ng.uebungen.erste.repository.NutzerRollenRepository;

@Component
public class Bootstrap implements ApplicationListener<ApplicationReadyEvent>{

	
	private NutzerRepository nutzerRepo;
	private NutzerRollenRepository rollRepo;
	private EinkaufRepository einkaufRepo;
	private ArtikelRepository artikelRepo;
	private PasswordEncoder encoder;
	
	
	@Autowired
	public Bootstrap(NutzerRepository nutzerRepo,
						NutzerRollenRepository rollRepo,
							EinkaufRepository einkaufRepo,
								ArtikelRepository artikelRepo,
									PasswordEncoder encoder) {
		this.nutzerRepo=nutzerRepo;
		this.rollRepo=rollRepo;
		this.einkaufRepo=einkaufRepo;
		this.artikelRepo=artikelRepo;
		this.encoder=encoder;
	}
	
	
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		if(this.nutzerRepo.count()==0) {
			
			Artikel stuhl = new Artikel("Stuhl",10);
			Artikel	tisch = new Artikel("tisch",20);
			
			List<Artikel> einkaeufe = new ArrayList<Artikel>();
			
			einkaeufe.add(stuhl);
			einkaeufe.add(tisch);
			
			
			
			
			NutzerRolle admin = new NutzerRolle("Admin");
			NutzerRolle user = new NutzerRolle("User");
			
			
			Nutzer bob = new Nutzer("bob",this.encoder.encode("1234"));
			Nutzer noob = new Nutzer("nob",this.encoder.encode("1234"));
			
			bob.addRolle(admin);
			noob.addRolle(user);
			
			
			Einkauf erster = new Einkauf(bob,einkaeufe);
			
			this.rollRepo.save(admin);
			this.rollRepo.save(user);
			
			this.nutzerRepo.save(bob);
			this.nutzerRepo.save(noob);
			
			this.artikelRepo.save(stuhl);
			this.artikelRepo.save(tisch);
			
			this.einkaufRepo.save(erster);
		}
		
		
	}

}
