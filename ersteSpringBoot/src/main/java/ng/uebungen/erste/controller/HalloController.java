package ng.uebungen.erste.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HalloController {
	
	
	/*
	 * Uebersicht
	 */
	@GetMapping("/")
	public EntityModel<String> sayHello() {
		
			
		return new EntityModel<>("Hallo World",
									linkTo(HalloController.class).withSelfRel(),
									linkTo(NutzerController.class).withRel("nutzer"),
									linkTo(RollenController.class).withRel("rollen"),
									linkTo(EinkaufController.class).withRel("einkauf"),
									linkTo(ArtikelController.class).withRel("artikel")
								);
	}
	
}
