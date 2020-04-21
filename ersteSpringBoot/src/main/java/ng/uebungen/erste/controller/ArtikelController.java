package ng.uebungen.erste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ng.uebungen.erste.entity.Artikel;
import ng.uebungen.erste.repository.ArtikelRepository;

@RestController
@RequestMapping("/artikel")
public class ArtikelController {
	
	@Autowired
	ArtikelRepository artikelRepo;
	
	@GetMapping("")
	public List<Artikel> alleArtikel(){
		return artikelRepo.findAll();
	}
	

}
