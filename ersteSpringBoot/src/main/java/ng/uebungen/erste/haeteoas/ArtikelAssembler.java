package ng.uebungen.erste.haeteoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ng.uebungen.erste.controller.ArtikelController;
import ng.uebungen.erste.entity.Artikel;

@Component
public class ArtikelAssembler implements RepresentationModelAssembler<Artikel, EntityModel<Artikel>>{

	@Override
	public EntityModel<Artikel> toModel(Artikel artikel) {
		
		Link selfLink = linkTo(ArtikelController.class).slash(artikel.getId()).withSelfRel();
		Link allLink = linkTo(ArtikelController.class).withRel("artikel");
		
		return new EntityModel<Artikel>(artikel, selfLink,allLink);
	}

}
