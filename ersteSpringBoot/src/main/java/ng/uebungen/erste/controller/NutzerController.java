package ng.uebungen.erste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ng.uebungen.erste.entity.Einkauf;
import ng.uebungen.erste.entity.Nutzer;
import ng.uebungen.erste.entity.NutzerRolle;
import ng.uebungen.erste.repository.NutzerRepository;
import ng.uebungen.erste.exceptions.NotFoundException;

@RestController
@RequestMapping("/nutzer")
public class NutzerController {
	
	//Der Name für die Exception Ausgabe
	private String exName="Nutzer";
	
	//um plain password zu codieren
	@Autowired
	protected PasswordEncoder encoder;
	
	@Autowired
	protected NutzerRepository nutzerRepro;
	
	
	//root
	@GetMapping("")
	List<Nutzer> getAllNutzer2(){
				
		return nutzerRepro.findAll();
	}
	
	@PostMapping("")
	public Nutzer newNutzer(@RequestBody Nutzer nutzer) {
		///plain password codieren
		String password = nutzer.getPassword();
		nutzer.setPassword(encoder.encode(password));
		
		return nutzerRepro.save(nutzer);
	}
	
	//einzel
	@GetMapping("/{id}")
	public Nutzer getNutzerById(@PathVariable Long id) { 
		return nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id,exName));
	}
	
	@GetMapping("/{id}/rollen")
	public List<NutzerRolle> getNutzerRollen(@PathVariable Long id) {
		
		Nutzer nutzer = nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id,exName));
		
		return nutzer.getRollen();
	}
	
	@GetMapping("/{id}/einkaeufe")
	public List<Einkauf> getNutzerEinkaeufe(@PathVariable Long id) {
		
		Nutzer nutzer = nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id,exName));
		
		return nutzer.getEinkaeufe();
	}
	
	//TODO SetRolle, SetEinkaeufe, deleteNutzer
	

}
