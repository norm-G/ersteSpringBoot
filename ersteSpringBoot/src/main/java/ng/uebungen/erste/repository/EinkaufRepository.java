package ng.uebungen.erste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ng.uebungen.erste.entity.Einkauf;

public interface EinkaufRepository extends JpaRepository<Einkauf, Long>{

}
