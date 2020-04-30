package ng.uebungen.erste.haeteoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ng.uebungen.erste.controller.NutzerController;
import ng.uebungen.erste.entity.Nutzer;



@Component
public class NutzerAssembler implements RepresentationModelAssembler<Nutzer, EntityModel<Nutzer>>{

	@Override
	public EntityModel<Nutzer> toModel(Nutzer nutzer) {
		Link selfLink = linkTo(NutzerController.class).slash(nutzer.getId()).withSelfRel();
		Link rollenLink = linkTo(NutzerController.class).slash(nutzer.getId()).slash("rollen").withRel("rollen");
		Link einkaufLink = linkTo(NutzerController.class).slash(nutzer.getId()).slash("einkaeufe").withRel("einkaeufe");
		Link allLink = linkTo(NutzerController.class).withRel("nutzer");
		
		return new EntityModel<Nutzer>(nutzer,rollenLink,einkaufLink,selfLink,allLink);
	}

}
