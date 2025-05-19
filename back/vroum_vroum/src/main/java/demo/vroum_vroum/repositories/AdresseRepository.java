package demo.vroum_vroum.repositories;

import demo.vroum_vroum.entities.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdresseRepository extends JpaRepository<Adresse, Integer> {
    Optional<Adresse> findAdresseByNumeroAndRueAndVilleAndCodePostal(String numero, String rue, String ville, String codePostal);
}
