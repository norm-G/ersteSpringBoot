package ng.uebungen.erste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ng.uebungen.erste.entity.Artikel;

public interface ArtikelRepository extends JpaRepository<Artikel, Long> {

}
