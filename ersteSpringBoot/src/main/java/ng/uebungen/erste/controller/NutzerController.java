package ng.uebungen.erste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ng.uebungen.erste.entity.Einkauf;
import ng.uebungen.erste.entity.Nutzer;
import ng.uebungen.erste.entity.NutzerRolle;
import ng.uebungen.erste.repository.NutzerRepository;
import ng.uebungen.erste.repository.NutzerRollenRepository;
import ng.uebungen.erste.exceptions.NotFoundException;

@RestController
@RequestMapping("/nutzer")
public class NutzerController {
	
	//Der Name für die Exception Ausgabe
	private String exNutzerName="Nutzer";
	private String exRolleName="Rolle";
	private String exEinkaufName="Einkauf";
	
	//um plain password zu codieren
	@Autowired
	protected PasswordEncoder encoder;
	
	@Autowired
	protected NutzerRepository nutzerRepro;
	
	@Autowired
	protected NutzerRollenRepository rollenRepro;
	
	/*
	 * root
	 */
	
	@GetMapping("")
	List<Nutzer> getAllNutzer2(){
				
		return nutzerRepro.findAll();
	}
	
	/*
	 * Save
	 */
	@PostMapping("")
	public Nutzer newNutzer(@RequestBody Nutzer nutzer) {
		///plain password codieren
		String password = nutzer.getPassword();
		nutzer.setPassword(encoder.encode(password));
		
		return nutzerRepro.save(nutzer);
	}
	
	/*
	 * show einzel
	 */
	@GetMapping("/{id}")
	public Nutzer getNutzerById(@PathVariable Long id) { 
		return nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id,exNutzerName));
	}
	
	@GetMapping("/{id}/rollen")
	public List<NutzerRolle> getNutzerRollen(@PathVariable Long id) {
		
		Nutzer nutzer = nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id,exNutzerName));
		
		return nutzer.getRollen();
	}
	
	@GetMapping("/{id}/einkaeufe")
	public List<Einkauf> getNutzerEinkaeufe(@PathVariable Long id) {
		
		Nutzer nutzer = nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id,exNutzerName));
				
		return nutzer.getEinkaeufe();
	}
	
		
	/*
	 * Update 
	 * TODO
	 * SetEinkaeufe
	 * 
	 */
	@PutMapping("/{id}")
	public Nutzer updateNutzer(@PathVariable Long id, @RequestBody Nutzer nutzer) {
		Nutzer aktuellerNutzer = nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id, exNutzerName));
		aktuellerNutzer.setNutzername(nutzer.getName());
		aktuellerNutzer.setPassword(encoder.encode(nutzer.getPassword()));
		
		nutzerRepro.save(aktuellerNutzer);
		
		return aktuellerNutzer;
	}
	
	@PutMapping("/{id}/rollen")
	public Nutzer addRolle(@PathVariable Long id, @RequestBody NutzerRolle rolle) {
		Nutzer nutzer = nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id, exNutzerName));
		rolle = rollenRepro.findByBezeichnung(rolle.getBezeichnung());
		
		if(rolle==null) {
			throw new NotFoundException(exRolleName);
		}
		
		nutzer.addRolle(rolle);
		
		nutzerRepro.save(nutzer);
		return nutzer;
	}
	
	
	//delete
	@DeleteMapping("/{id}")
	public void deleteNutzer(@PathVariable Long id) {
		nutzerRepro.deleteById(id);
		
	}
	
	
	
	
	

}
