package ng.uebungen.erste.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ng.uebungen.erste.entity.Einkauf;
import ng.uebungen.erste.repository.EinkaufRepository;

@RestController
@RequestMapping("/einkauf")
public class EinkaufController {
	
	
	@Autowired
	EinkaufRepository einkaufRepo;
	
	
	@GetMapping("")
	public List<Einkauf> alleEinkaeufe(){
		return einkaufRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Einkauf> einkaufPerId(@PathVariable Long id) {
		return einkaufRepo.findById(id);
	}

}
