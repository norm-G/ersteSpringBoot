package ng.uebungen.erste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Artikel> alleArtikel(){
		return artikelRepo.findAll();
	}
	
	@PostMapping("")
	public Artikel newArtikel(@RequestBody Artikel artikel) {
		return artikelRepo.save(artikel);
	}
	
	@GetMapping("/{id}")
	public Artikel getArtikel(@PathVariable Long id) {
		return artikelRepo.findById(id).orElseThrow(()->new NotFoundException(id, exArtikel));
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
