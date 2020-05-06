package ng.uebungen.erste.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import ng.uebungen.erste.entity.Artikel;
import ng.uebungen.erste.entity.Einkauf;
import ng.uebungen.erste.entity.Nutzer;
import ng.uebungen.erste.exceptions.NotFoundException;
import ng.uebungen.erste.haeteoas.ArtikelAssembler;
import ng.uebungen.erste.haeteoas.EinkaufAssembler;
import ng.uebungen.erste.haeteoas.NutzerAssembler;
import ng.uebungen.erste.repository.ArtikelRepository;
import ng.uebungen.erste.repository.EinkaufRepository;


@RestController
@RequestMapping("/einkauf")
public class EinkaufController {
	
	private String exEinkauf = "Einkauf";
	private String exArtikel = "Artikel";
	
	@Autowired
	EinkaufRepository einkaufRepo;
	
	@Autowired
	ArtikelRepository artikelRepro;
	
	@Autowired
	EinkaufAssembler einkaufAssembler;
	
	@Autowired
	NutzerAssembler nutzerAssembler;
	
	@Autowired 
	ArtikelAssembler artikelAssembler;
	
	/*
	 * root
	 */
	@GetMapping("")
	public ResponseEntity<?> alleEinkaeufe(){
		
		List<?> einkaeufe = einkaufRepo.findAll().stream()
													.map(einkaufAssembler::toModel)
													.collect(Collectors.toList());
		CollectionModel<?> einkaeufeLinked= new CollectionModel<>(einkaeufe, linkTo(EinkaufController.class).withSelfRel());
		
		
		return ResponseEntity.status(HttpStatus.OK).body(einkaeufeLinked) ;
	}
	
	@PostMapping("")
	public ResponseEntity<?> newEinkauf(@RequestBody Einkauf einkauf) {
		return ResponseEntity.status(HttpStatus.CREATED).body(einkaufAssembler.toModel(einkaufRepo.save(einkauf)));
	}
	
	
	/*
	 * einzel
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> einkaufPerId(@PathVariable Long id) {
		
		Einkauf einkauf = einkaufRepo.findById(id).orElseThrow(()->new NotFoundException(id, exEinkauf));
		
		return ResponseEntity.status(HttpStatus.OK).body(einkaufAssembler.toModel(einkauf));
	}
	
	
	@GetMapping("/{id}/nutzer")
	public ResponseEntity<?> einkaufsNutzer(@PathVariable Long id) {
		
		Einkauf einkauf = einkaufRepo.findById(id).orElseThrow(()-> new NotFoundException(id, exEinkauf));
		
		// um _embedded zu erreichen
		List<EntityModel<Nutzer>> nutzerLinked = new ArrayList<>();
		nutzerLinked.add(nutzerAssembler.toModel(einkauf.getNutzer()));
				
		CollectionModel<?> allLinked = new CollectionModel<>(nutzerLinked, 
																	linkTo(EinkaufController.class)
																			.slash(einkauf.getId())
																			.withRel("einkauf"));
		
		
		return ResponseEntity.status(HttpStatus.OK).body(allLinked);
	}
	
	@GetMapping("/{id}/artikel")
	public ResponseEntity<?> einkaufsArtikel(@PathVariable Long id){
		Einkauf einkauf = einkaufRepo.findById(id).orElseThrow(()-> new NotFoundException(id, exEinkauf));
		List<?> artikel = einkauf.getArtikel().stream()
												.map(artikelAssembler::toModel)
												.collect(Collectors.toList());
				
		CollectionModel<?> allArtikel = new CollectionModel<>(artikel,linkTo(EinkaufController.class)
																		.slash(einkauf.getId())
																		.withRel("einkauf"));
				
		return ResponseEntity.status(HttpStatus.OK).body(allArtikel);
	}
	
	
	/*
	 * add Artikel
	 * 
	 */		
	@PutMapping("/{id}/artikel")
	public ResponseEntity<?> addArtikel(@PathVariable Long id, @RequestBody Artikel artikel) {
		
		Einkauf einkauf = einkaufRepo.findById(id).orElseThrow(()-> new NotFoundException(id, exEinkauf));
		Artikel newArtikel = artikelRepro.findById(artikel.getId()).orElseThrow(()-> new NotFoundException(artikel.getId(),exArtikel));
		
	
		einkauf.addArtikel(newArtikel);
		einkaufRepo.save(einkauf);
		
		List<?> artikelLinked = einkauf.getArtikel().stream().map(artikelAssembler::toModel).collect(Collectors.toList());
		
		CollectionModel<?> allArtikelLinked = new CollectionModel<>(artikelLinked, linkTo(EinkaufController.class)
																						.slash(einkauf.getId())
																						.withRel("einkauf"));
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(allArtikelLinked); 
	}

	
	
	/*
	 * delete
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEinakuf(@PathVariable Long id) {
		einkaufRepo.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
	}
	
	
	
	@DeleteMapping("/{einkaufId}/artikel/{artikelId}")
	public ResponseEntity<?> deleteArtikelFromEinkauf(
				@PathVariable(name = "einkaufId") Long einkaufId,
					@PathVariable(name = "artikelId") Long artikelId) {
		
		Einkauf einkauf = einkaufRepo.findById(einkaufId).orElseThrow(()-> new NotFoundException(einkaufId, exEinkauf));
		Artikel artikel = artikelRepro.findById(artikelId).orElseThrow(()-> new NotFoundException(artikelId, exArtikel));
		einkauf.removeArtiekl(artikel);
		einkaufRepo.save(einkauf);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
	}
}
