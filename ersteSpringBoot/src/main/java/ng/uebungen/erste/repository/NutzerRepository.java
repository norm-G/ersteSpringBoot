package ng.uebungen.erste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ng.uebungen.erste.entity.Nutzer;


public interface NutzerRepository extends JpaRepository<Nutzer, Long>{
		Nutzer findByName(String name);
}
