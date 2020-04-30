package ng.uebungen.erste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.server.core.Relation;

import ng.uebungen.erste.entity.NutzerRolle;


public interface NutzerRollenRepository extends JpaRepository<NutzerRolle, Long> {
	
	NutzerRolle findByBezeichnung(String bezeichnung);
}
