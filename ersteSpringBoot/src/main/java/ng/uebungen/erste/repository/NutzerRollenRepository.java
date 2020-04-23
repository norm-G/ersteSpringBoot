package ng.uebungen.erste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ng.uebungen.erste.entity.NutzerRolle;

public interface NutzerRollenRepository extends JpaRepository<NutzerRolle, Long> {
	
	NutzerRolle findByBezeichnung(String bezeichnung);
}
