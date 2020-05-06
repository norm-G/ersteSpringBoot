package ng.uebungen.erste.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
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
import ng.uebungen.erste.exceptions.NotFoundException;
import ng.uebungen.erste.haeteoas.ArtikelAssembler;
import ng.uebungen.erste.repository.ArtikelRepository;

@RestController
@RequestMapping("/artikel")
public class ArtikelController {
	
	
	private String exArtikel = "Artikel";
	
	@Autowired
	ArtikelRepository artikelRepo;
	
	@Autowired
	ArtikelAssembler artikelAssembler;
	
	
	@GetMapping("")
	public ResponseEntity<?> alleArtikel(){
		
		/* Ohne Assembler
		 
		List<Artikel> artikel = artikelRepo.findAll();
		
		for(Artikel zArtikel:artikel) {
			Link selfLink = linkTo(methodOn(ArtikelController.class).einArtikel(zArtikel.getId())).withSelfRel();
			zArtikel.add(selfLink);
		}
		
		 
		return ResponseEntity.status(HttpStatus.OK).body(new CollectionModel<Artikel>(artikel,
									linkTo(ArtikelController.class).withSelfRel()));
		*/
		
		List<?> artikel = artikelRepo.findAll().stream()
												.map(artikelAssembler::toModel)
												.collect(Collectors.toList());
		CollectionModel<?> artikelLinked = new CollectionModel<>(artikel,linkTo(ArtikelController.class).withSelfRel());
		
		
		return ResponseEntity.status(HttpStatus.OK).body(artikelLinked);
				
	}
	
	@PostMapping("")
	public ResponseEntity<?> newArtikel(@RequestBody Artikel artikel) {
		Artikel zArtikel = artikelRepo.save(artikel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(artikelAssembler.toModel(zArtikel));
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> einArtikel(@PathVariable Long id){
		Artikel artikel = artikelRepo.findById(id).orElseThrow(()->new NotFoundException(id, exArtikel));
		
		return ResponseEntity.status(HttpStatus.OK).body(artikelAssembler.toModel(artikel));
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateArtikel(@PathVariable Long id, @RequestBody Artikel artikel) {
		//entweder findet er den artikel und updated oder erzeugt einen neuen 
		return artikelRepo.findById(id).map(
										zArtikel->{
											zArtikel.setName(artikel.getName());
											zArtikel.setPreis(artikel.getPreis());
											artikelRepo.save(zArtikel);
											return ResponseEntity.status(HttpStatus.ACCEPTED)
																	.body(artikelAssembler.toModel(zArtikel));
										}).orElseGet(()->{
											artikelRepo.save(artikel);
											return ResponseEntity.status(HttpStatus.CREATED)
																	.body(artikelAssembler.toModel(artikel));
										}); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteArtikel(@PathVariable Long id) {
		artikelRepo.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
	}
	
}
