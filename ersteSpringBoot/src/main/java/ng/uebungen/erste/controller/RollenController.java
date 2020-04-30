package ng.uebungen.erste.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ng.uebungen.erste.entity.Nutzer;
import ng.uebungen.erste.entity.NutzerRolle;
import ng.uebungen.erste.repository.NutzerRollenRepository;
import ng.uebungen.erste.exceptions.NotFoundException;
import ng.uebungen.erste.haeteoas.NutzerAssembler;
import ng.uebungen.erste.haeteoas.RolleAssembler;;

@RestController
@RequestMapping("/rollen")
public class RollenController {
	
	
	//Der Name für die Exception Ausgabe
	private String exName="Rolle";
	
	
	@Autowired
	NutzerRollenRepository rollenRepro;
	
	@Autowired
	RolleAssembler rolleAsembler;
	
	@Autowired
	NutzerAssembler nutzerAsembler;
	
	
	
	
	//Alle
	@GetMapping("")
	public ResponseEntity<?> alleRollen(){
		
/* Variante ohne Asembler
 * 
 * 	
 * List<NutzerRolle> rollen = rollenRepro.findAll();
		List<EntityModel<NutzerRolle>> eRollen = new ArrayList<EntityModel<NutzerRolle>>();
		CollectionModel<EntityModel<NutzerRolle>> nutzerRollen;
		
		
		for(NutzerRolle rolle: rollen) {
			Link self = linkTo(RollenController.class).slash(rolle.getId()).withSelfRel();
			EntityModel<NutzerRolle> eRolle = new EntityModel<NutzerRolle>(rolle, self);
			//rolle.add(self);
			eRollen.add(eRolle);
		}
		
		nutzerRollen = new CollectionModel<EntityModel<NutzerRolle>>(eRollen,linkTo(RollenController.class).withSelfRel());	
 */
		

		CollectionModel<EntityModel<NutzerRolle>> rollen;
		
		List<EntityModel<NutzerRolle>> rollens= rollenRepro.findAll()
												.stream()
												.map(rolleAsembler::toModel)
												.collect(Collectors.toList());
		
		rollen = new CollectionModel<EntityModel<NutzerRolle>>(rollens,linkTo(RollenController.class).withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK)
								.body(rollen);
	}
	
	
	//Neu
	@PostMapping("")
	public ResponseEntity<EntityModel<NutzerRolle>> newRoll(@RequestBody NutzerRolle rolle) {
		return ResponseEntity.status(HttpStatus.CREATED)
								.body(rolleAsembler.toModel(rollenRepro.save(rolle)));
	}
	
	//Einzel
	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<NutzerRolle>> getRolle(@PathVariable Long id) {
		
		NutzerRolle rolle = rollenRepro.findById(id).orElseThrow(()-> new NotFoundException(id,exName));
		
		return ResponseEntity.status(HttpStatus.OK)
								.body(rolleAsembler.toModel(rolle));
	}
	
	//Nutzer per Rolle
	@GetMapping("/{id}/nutzer")
	public ResponseEntity<CollectionModel<EntityModel<Nutzer>>> getNutzerPerRolle(@PathVariable Long id){
		
		NutzerRolle rolle = rollenRepro.findById(id).orElseThrow(()-> new NotFoundException(id,exName));
		CollectionModel<EntityModel<Nutzer>> nutzers;
		
		List<EntityModel<Nutzer>> nutzer= rolle.getNutzer()
											.stream()
											.map(nutzerAsembler::toModel)
											.collect(Collectors.toList());
		
		nutzers = new CollectionModel<EntityModel<Nutzer>>(nutzer,linkTo(RollenController.class).withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK)
								.body(nutzers);
	}
	
	
	//Update oder Neu
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<NutzerRolle>> updateRolle(@PathVariable Long id, @RequestBody NutzerRolle rolle) {
		
		return rollenRepro.findById(id).map(
						zwischenRolle -> {
								//kein setID da ich autoID nutzen will 
								zwischenRolle.setBezeichnung(rolle.getBezeichnung());
								return ResponseEntity.status(HttpStatus.OK)
														.body(rolleAsembler.toModel(rollenRepro.save(zwischenRolle)));
							}).orElseGet(()->{
								return ResponseEntity.status(HttpStatus.CREATED)
														.body(rolleAsembler.toModel(rollenRepro.save(rolle)));
							});	
	}
	//Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRolle (@PathVariable Long id) {
		rollenRepro.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
								.body(null);
	}
	
	
	/** Fuegt Links zur Rolle hinzu 
	 * 
	 * @exception Check auf Existens ervorderlich
	 */
//	private EntityModel<NutzerRolle> addLinks(NutzerRolle rolle){
//		return new EntityModel<>(rolle,
//								linkTo(RollenController.class).slash(rolle.getId()).withSelfRel(),
//								linkTo(RollenController.class).withRel("rollen")
//								); 
//	}
	
	
}
