package ng.uebungen.erste.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ng.uebungen.erste.entity.NutzerRolle;
import ng.uebungen.erste.repository.NutzerRollenRepository;
import ng.uebungen.erste.exceptions.NotFoundException;;

@RestController
@RequestMapping("/rollen")
public class RollenController {
	
	
	//Der Name für die Exception Ausgabe
	private String exName="Rolle";
	
	
	@Autowired
	NutzerRollenRepository rollen;
	
	//Root
	@GetMapping("")
	public ResponseEntity<List<NutzerRolle>> alleRollen(){
		return ResponseEntity.ok(rollen.findAll());
	}
	
	@PostMapping("")
	public NutzerRolle newRoll(@RequestBody NutzerRolle rolle) {
		return rollen.save(rolle);
	}
	
	//einzel
	@GetMapping("/{id}")
	public NutzerRolle getRolle(@PathVariable Long id) {
		return rollen.findById(id).orElseThrow(()-> new NotFoundException(id,exName));
	}
	
	@PutMapping("/{id}")
	public NutzerRolle updateRolle(@PathVariable Long id, @RequestBody NutzerRolle rolle) {
		
		return rollen.findById(id).map(
						zwischenRolle -> {
								zwischenRolle.setBezeichnung(rolle.getBezeichnung());
								return rollen.save(zwischenRolle);
							}).orElseGet(()->{
								//kein setID da ich autoID nutzen will 
								return rollen.save(rolle);
							});	
	}
	
	@DeleteMapping("/{id}")
	public void deleteRolle (@PathVariable Long id) {
		rollen.deleteById(id);
	}
	
	
	
}
