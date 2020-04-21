package ng.uebungen.erste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ng.uebungen.erste.entity.NutzerRolle;
import ng.uebungen.erste.repository.NutzerRollenRepository;

@RestController
@RequestMapping("/rollen")
public class RollenController {

	@Autowired
	NutzerRollenRepository rollen;
	
	
	@GetMapping("")
	public ResponseEntity<List<NutzerRolle>> alleRollen(){
		return ResponseEntity.ok(rollen.findAll());
	}
	
}
