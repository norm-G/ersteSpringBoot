package ng.uebungen.erste.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
	
	@GetMapping("/welcome")
	public String sayWelcome(ModelMap model) {
		
		model.put("name","Bob");
		
		return "welcome";
	}
	@GetMapping("/welcome2")
	public String sayWelcomeZwei(ModelMap model) {
		
		model.put("name","Bobi");
		
		return "welcome";
	}
}
