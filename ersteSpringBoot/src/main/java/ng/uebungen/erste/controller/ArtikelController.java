package ng.uebungen.erste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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
import ng.uebungen.erste.repository.ArtikelRepository;

@RestController
@RequestMapping("/artikel")
public class ArtikelController {
	
	
	private String exArtikel = "Artikel";
	
	@Autowired
	ArtikelRepository artikelRepo;
	
	@GetMapping("")
	public CollectionModel<Artikel> alleArtikel(){
		
		List<Artikel> artikel = artikelRepo.findAll();
		
		for(Artikel zArtikel:artikel) {
			Link selfLink = linkTo(methodOn(ArtikelController.class).einArtikel(zArtikel.getId())).withSelfRel();
			zArtikel.add(selfLink);
		}
		
		 
		return new CollectionModel<>(artikel,
									linkTo(ArtikelController.class).withSelfRel());
	}
	
	@PostMapping("")
	public EntityModel<Artikel> newArtikel(@RequestBody Artikel artikel) {
		Artikel zArtikel = artikelRepo.save(artikel);
		return new EntityModel<>(zArtikel,
				linkTo(ArtikelController.class).slash(zArtikel.getId()).withSelfRel(),
				linkTo(ArtikelController.class).withRel("artikel")
				);
	}
	
	
	@GetMapping("/{id}")
	public EntityModel<Artikel> einArtikel(@PathVariable Long id){
		Artikel artikel = artikelRepo.findById(id).orElseThrow(()->new NotFoundException(id, exArtikel));
		
		return new EntityModel<>(artikel,
								linkTo(ArtikelController.class).slash(artikel.getId()).withSelfRel(),
								linkTo(ArtikelController.class).withRel("artikel")
								);
	}
	
	
	
	@PutMapping("/{id}")
	public Artikel updateArtikel(@PathVariable Long id, @RequestBody Artikel artikel) {
		//entweder findet er den artikel und updated oder erzeugt einen neuen 
		return artikelRepo.findById(id).map(
										zArtikel->{
											zArtikel.setName(artikel.getName());
											zArtikel.setPreis(artikel.getPreis());
											return artikelRepo.save(zArtikel);
										}).orElseGet(()->{
											return artikelRepo.save(artikel);
										}); 
	}
	
	@DeleteMapping("/{id}")
	public void deleteArtikel(@PathVariable Long id) {
		artikelRepo.deleteById(id);
	}
	
}
