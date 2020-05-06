package ng.uebungen.erste.haeteoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ng.uebungen.erste.controller.EinkaufController;
import ng.uebungen.erste.entity.Einkauf;

@Component
public class EinkaufAssembler implements RepresentationModelAssembler<Einkauf, EntityModel<Einkauf>>{

	@Override
	public EntityModel<Einkauf> toModel(Einkauf einkauf) {
		
		Link selfLink = linkTo(EinkaufController.class).slash(einkauf.getId()).withRel("einkauf");
		Link nutzerLink = linkTo(EinkaufController.class).slash(einkauf.getId()).slash("nutzer").withRel("nutzer");
		Link artikelLink = linkTo(EinkaufController.class).slash(einkauf.getId()).slash("artikel").withRel("artikel");
		Link allLink = linkTo(EinkaufController.class).withSelfRel();
		
		
		return new EntityModel<Einkauf>(einkauf , selfLink , nutzerLink , artikelLink , allLink);
	}

}
