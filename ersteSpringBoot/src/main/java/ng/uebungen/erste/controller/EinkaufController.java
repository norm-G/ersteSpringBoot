package ng.uebungen.erste.controller;

import java.util.ArrayList;
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
import ng.uebungen.erste.entity.Einkauf;
import ng.uebungen.erste.entity.Nutzer;
import ng.uebungen.erste.exceptions.NotFoundException;
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
	
	/*
	 * root
	 */
	@GetMapping("")
	public List<Einkauf> alleEinkaeufe(){
		return einkaufRepo.findAll();
	}
	
	@PostMapping("")
	public Einkauf newEinkauf(@RequestBody Einkauf einkauf) {
		return einkaufRepo.save(einkauf);
	}
	
	
	/*
	 * einzel
	 */
	@GetMapping("/{id}")
	public Einkauf einkaufPerId(@PathVariable Long id) {
		return einkaufRepo.findById(id).orElseThrow(()->new NotFoundException(id, exEinkauf));
	}
	
	
	@GetMapping("/{id}/nutzer")
	public Nutzer einkaufsNutzer(@PathVariable Long id) {
		
		Einkauf einkauf = einkaufRepo.findById(id).orElseThrow(()-> new NotFoundException(id, exEinkauf));
	
		return einkauf.getNutzer();
	}
	
	@GetMapping("/{id}/artikel")
	public List<Artikel> einkaufsArtikel(@PathVariable Long id){
		Einkauf einkauf = einkaufRepo.findById(id).orElseThrow(()-> new NotFoundException(id, exEinkauf));
		return einkauf.getArtikel();
	}
	
	
	/*
	 * Update artikel
	 */
	/*
	@PutMapping("/{id}/artikel")
	public Einkauf addArtikel(@PathVariable Long id, @RequestBody Artikel artikel) {
		Einkauf einkauf = einkaufRepo.findById(id).orElseThrow(()-> new NotFoundException(id, exEinkauf));
		
		artikel = artikelRepro.findById(artikel.getId()).orElseThrow(()-> new NotFoundException(exArtikel));
		einkauf.addArtikel(artikel);
		
		return einkaufRepo.save(einkauf); 
	}*/
	
	@PutMapping("/{id}/artikel")
	public Einkauf addListeVonArtikel(@PathVariable Long id, @RequestBody List<Artikel> artikel) {
		Einkauf einkauf = einkaufRepo.findById(id).orElseThrow(()-> new NotFoundException(id, exEinkauf));
		
		List<Artikel> lArtikel = new ArrayList<Artikel>();
		
		for(Artikel einArtikel: artikel) {
			lArtikel.add(artikelRepro.findById(einArtikel.getId()).orElseThrow(()-> new NotFoundException(einArtikel.getId(),exArtikel)));
		}
		
		einkauf.setArtikel(lArtikel);
		
		return einkaufRepo.save(einkauf); 
	}

	
	
	/*
	 * delete
	 */
	@DeleteMapping("/{id}")
	public void deleteEinakuf(@PathVariable Long id) {
		einkaufRepo.deleteById(id);
	}
	
	@DeleteMapping("/{einkaufId}/artikel/{artikelId}")
	public void deleteArtikelFromEinkauf(
				@PathVariable(name = "einkaufId") Long einkaufId,
					@PathVariable(name = "artikelId") Long artikelId) {
		
		Einkauf einkauf = einkaufRepo.findById(einkaufId).orElseThrow(()-> new NotFoundException(einkaufId, exEinkauf));
		Artikel artikel = artikelRepro.findById(artikelId).orElseThrow(()-> new NotFoundException(artikelId, exArtikel));
		einkauf.removeArtiekl(artikel);
		einkaufRepo.save(einkauf);
	}
}
