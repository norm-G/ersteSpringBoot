package ng.uebungen.erste.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HalloController {
	
	
	@GetMapping("/")
	public String sayHello() {
		return "Hello world!2";
	}
	
	
	
/*	@GetMapping("/logout")
	public String sayLogout() {
		return "Du bist ausgelogt!";
	}
	*/
}
