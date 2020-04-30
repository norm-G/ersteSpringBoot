package ng.uebungen.erste.haeteoas;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ng.uebungen.erste.controller.RollenController;
import ng.uebungen.erste.entity.NutzerRolle;

@Component
public class RolleAssembler implements RepresentationModelAssembler<NutzerRolle, EntityModel<NutzerRolle>>{

	@Override
	public EntityModel<NutzerRolle> toModel(NutzerRolle rolle) {
		
		Link selfLink = linkTo(RollenController.class).slash(rolle.getId()).withSelfRel();
		Link allLink = linkTo(RollenController.class).withRel("rollen");
		
		return new EntityModel<NutzerRolle>(rolle,selfLink,allLink);
	}

}
