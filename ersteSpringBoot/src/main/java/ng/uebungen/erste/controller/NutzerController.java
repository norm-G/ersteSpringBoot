package ng.uebungen.erste.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ng.uebungen.erste.entity.Nutzer;
import ng.uebungen.erste.repository.NutzerRepository;
import ng.uebungen.erste.repository.NutzerRollenRepository;

@RestController
@RequestMapping("/nutzer")
public class NutzerController {
	
	@Autowired
	protected NutzerRepository nutzerRepro;
	
	@Autowired
	protected NutzerRollenRepository rollenRepro;
	
		
	@GetMapping("")
	public List<Nutzer> getAllNutzer2(){
				
		return nutzerRepro.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Nutzer> getNutzerById(@PathVariable Long id) { 
		return nutzerRepro.findById(id);
	}

}
