package ng.uebungen.erste.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ng.uebungen.erste.haeteoas.NutzerAssembler;
import ng.uebungen.erste.haeteoas.RolleAssembler;

@RestController
@RequestMapping("/nutzer")
public class NutzerController {
	
	//Der Name für die Exception Ausgabe
	private String exNutzerName="Nutzer";
	private String exRolleName="Rolle";
	
	
	//um plain password zu codieren
	@Autowired
	protected PasswordEncoder encoder;
		
	@Autowired
	protected NutzerRepository nutzerRepro;
	
	@Autowired
	protected NutzerRollenRepository rollenRepro;
	
	@Autowired
	protected NutzerAssembler nutzerAssembler;
	
	@Autowired
	protected RolleAssembler rollenAssembler;
	
	/*
	 * root
	 */
	
	@GetMapping("")
	public ResponseEntity<?> getAllNutzer(){
		
		List<?> nutzer = nutzerRepro.findAll().stream()
															.map(nutzerAssembler::toModel)
															.collect(Collectors.toList());
		CollectionModel<?> nutzerList = new CollectionModel<>(nutzer, linkTo(NutzerController.class).withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(nutzerList);
	}
	
	/*
	 * Save
	 */
	@PostMapping("")
	public ResponseEntity<?> newNutzer(@RequestBody Nutzer nutzer) {
		///plain password codieren
		String password = nutzer.getPassword();
		nutzer.setPassword(encoder.encode(password));
		
		nutzerRepro.save(nutzer);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(nutzerAssembler.toModel(nutzer));
	}
	
	/*
	 * show einzel
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getNutzerById(@PathVariable Long id) { 
		
		Nutzer nutzer = nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id,exNutzerName));
		 
		return ResponseEntity.status(HttpStatus.OK).body(nutzerAssembler.toModel(nutzer));
	}
	
	@GetMapping("/{id}/rollen")
	public ResponseEntity<?> getNutzerRollen(@PathVariable Long id) {
		
		Nutzer nutzer = nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id,exNutzerName));
		
		List<?> listRollen = nutzer.getRollen().stream()
																		.map(rollenAssembler::toModel)
																		.collect(Collectors.toList());
		CollectionModel<?> nutzerRollen = new CollectionModel<>(listRollen,linkTo(NutzerController.class).slash(id).withRel("nutzer"));
		
		return ResponseEntity.status(HttpStatus.OK).body(nutzerRollen);
	}
	
	
	/*
	 * TODO:
	 * in ResponseEntity ändern
	 */
	 
	@GetMapping("/{id}/einkaeufe")
	public List<Einkauf> getNutzerEinkaeufe(@PathVariable Long id) {
		
		Nutzer nutzer = nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id,exNutzerName));
				
		return nutzer.getEinkaeufe();
	}
	
		
	//update oder create Nutzer
	@PutMapping("/{id}")
	public ResponseEntity<?> updateNutzer(@PathVariable Long id, @RequestBody Nutzer nutzer) {

		
		return nutzerRepro.findById(id).map(
									zNutzer ->{
												zNutzer.setNutzername(nutzer.getName());
												zNutzer.setPassword(encoder.encode(nutzer.getPassword()));
												nutzerRepro.save(zNutzer);
												return ResponseEntity.status(HttpStatus.ACCEPTED)
																		.body(nutzerAssembler.toModel(zNutzer));
									}).orElseGet(()->{
											nutzer.setPassword(encoder.encode(nutzer.getPassword()));
											nutzerRepro.save(nutzer);
											return ResponseEntity.status(HttpStatus.CREATED)
																	.body(nutzerAssembler.toModel(nutzer));
									});

	}
	
	//add Rolle
	@PutMapping("/{id}/rollen")
	public ResponseEntity<?> addRolle(@PathVariable Long id, @RequestBody NutzerRolle rolle) {
		
		Nutzer nutzer = nutzerRepro.findById(id).orElseThrow(()-> new NotFoundException(id, exNutzerName));		
		
		nutzer.addRolle(rollenRepro.findById(rolle.getId()).orElseThrow(()-> new NotFoundException(exRolleName)));
		
		nutzerRepro.save(nutzer);
		
		List<?> rollenList = nutzer.getRollen().stream()
												.map(rollenAssembler::toModel)
												.collect(Collectors.toList());
		
		CollectionModel<?>nutzerRollen = new CollectionModel<>(rollenList,linkTo(NutzerController.class).slash(id).withRel("nutzer"));
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(nutzerRollen);
	}
	
	
	
	//delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNutzer(@PathVariable Long id) {
		nutzerRepro.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
	}
	
	
	
	
	

}
