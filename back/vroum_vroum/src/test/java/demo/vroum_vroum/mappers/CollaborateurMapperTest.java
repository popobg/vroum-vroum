package demo.vroum_vroum.mappers;

import org.junit.jupiter.api.Test;

import demo.vroum_vroum.dto.CollaborateurDto;
import demo.vroum_vroum.dto.CollaborateurLiteDto;
import demo.vroum_vroum.dto.VehiculeLiteDto;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Vehicule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires du mapper entité Collaborateur <-> dtos de Collaborateur
 */
class CollaborateurMapperTest {
    private int id = 1;

    // DATA
    private final List<Collaborateur> collaborateurs = new ArrayList<>();

    private final List<CollaborateurLiteDto> collaborateursLiteDto = new ArrayList<>();

    private final List<CollaborateurDto> collaborateursDto = new ArrayList<>();

    /**
     * Constructeur
     */
    public CollaborateurMapperTest() {
        // Collaborateur
        Collaborateur collaborateur1 = new Collaborateur(1, "Dupont", "Jean", "3 avenue du Général de Gaulle", "jean.dupont@example.com", "6010203040", "jdupont", "Password1!", true);
        Collaborateur collaborateur2 = new Collaborateur(2, "Martin", "Marie", "12 impasse des Alpes", "marie.martin@example.com", "6050607080", "mmartin", "Password2!", false);
        Collaborateur collaborateur3 = new Collaborateur(3, "Durant", "Pierre", "5 grande rue des prés", "pierre.durant@example.com", "6090101112", "pdurant", "Password3!", true);

        Vehicule vehicule1 = new Vehicule(1, 5, "Fiesta", "Ford", "BG069WC");
        Vehicule vehicule2 = new Vehicule(2, 5, "208", "Peugeot", "QT406CB");
        Vehicule vehicule3 = new Vehicule(3, 4, "Zoe", "Renault", "JW733CG");

        List<Vehicule> vehicules = new ArrayList<>();
        vehicules.add(vehicule1);
        vehicules.add(vehicule2);

        collaborateur1.setVehicules(vehicules);
        collaborateur3.setVehicules(List.of(vehicule3));

        this.collaborateurs.add(collaborateur1);
        this.collaborateurs.add(collaborateur2);
        this.collaborateurs.add(collaborateur3);

        // CollaborateurDto
        CollaborateurDto collaborateurDto1 = new CollaborateurDto(collaborateur1.getId(), collaborateur1.getNom(), collaborateur1.getPrenom(), collaborateur1.getAdresse(), collaborateur1.getEmail(), collaborateur1.getTelephone(), collaborateur1.getPseudo(), collaborateur1.getPassword(), collaborateur1.getAdmin());
        CollaborateurDto collaborateurDto2 = new CollaborateurDto(collaborateur2.getId(), collaborateur2.getNom(), collaborateur2.getPrenom(), collaborateur2.getAdresse(), collaborateur2.getEmail(), collaborateur2.getTelephone(), collaborateur2.getPseudo(), collaborateur2.getPassword(), collaborateur2.getAdmin());
        CollaborateurDto collaborateurDto3 = new CollaborateurDto(collaborateur3.getId(), collaborateur3.getNom(), collaborateur3.getPrenom(), collaborateur3.getAdresse(), collaborateur3.getEmail(), collaborateur3.getTelephone(), collaborateur3.getPseudo(), collaborateur3.getPassword(), collaborateur3.getAdmin());

        VehiculeLiteDto vehiculeDto1 = new VehiculeLiteDto(vehicule1.getId(), vehicule1.getMarque(), vehicule1.getModele());
        VehiculeLiteDto vehiculeDto2 = new VehiculeLiteDto(vehicule2.getId(), vehicule2.getMarque(), vehicule2.getModele());
        VehiculeLiteDto vehiculeDto3 = new VehiculeLiteDto(vehicule3.getId(), vehicule3.getMarque(), vehicule3.getModele());

        List<VehiculeLiteDto> vehiculesDto = new ArrayList<>();
        vehiculesDto.add(vehiculeDto1);
        vehiculesDto.add(vehiculeDto2);

        collaborateurDto1.setVehicules(vehiculesDto);
        collaborateurDto3.setVehicules(List.of(vehiculeDto3));

        this.collaborateursDto.add(collaborateurDto1);
        this.collaborateursDto.add(collaborateurDto2);
        this.collaborateursDto.add(collaborateurDto3);

        // CollaborateurLiteDto
        CollaborateurLiteDto collaborateurLiteDto1 = new CollaborateurLiteDto(collaborateur1.getId(), collaborateur1.getNom(), collaborateur1.getPrenom(), collaborateur1.getTelephone());
        CollaborateurLiteDto collaborateurLiteDto2 = new CollaborateurLiteDto(collaborateur2.getId(), collaborateur2.getNom(), collaborateur2.getPrenom(), collaborateur2.getTelephone());
        CollaborateurLiteDto collaborateurLiteDto3 = new CollaborateurLiteDto(collaborateur3.getId(), collaborateur3.getNom(), collaborateur3.getPrenom(), collaborateur3.getTelephone());

        this.collaborateursLiteDto.add(collaborateurLiteDto1);
        this.collaborateursLiteDto.add(collaborateurLiteDto2);
        this.collaborateursLiteDto.add(collaborateurLiteDto3);
    }

    @Test
    void testToLiteDtoOK() {
        Collaborateur inputCollaborateur = this.collaborateurs.stream()
        .filter(c -> c.getId() == this.id)
        .findFirst()
        .orElseThrow();

        CollaborateurLiteDto expectedCollaborateurLiteDto = this.collaborateursLiteDto.stream()
        .filter(c -> c.getId() == this.id)
        .findFirst()
        .orElseThrow();

        CollaborateurLiteDto actualCollaborateurLiteDto = CollaborateurMapper.toLiteDto(inputCollaborateur);

        assertEquals(expectedCollaborateurLiteDto.getId(), actualCollaborateurLiteDto.getId());
        assertEquals(expectedCollaborateurLiteDto.getNom(), actualCollaborateurLiteDto.getNom());
        assertEquals(expectedCollaborateurLiteDto.getPrenom(), actualCollaborateurLiteDto.getPrenom());
        assertEquals(expectedCollaborateurLiteDto.getTelephone(), actualCollaborateurLiteDto.getTelephone());
    }

    @Test
    void testToLiteDtosOK() {
        Set<CollaborateurLiteDto> actualCollaborateursLiteDto = CollaborateurMapper.toLiteDtos(new HashSet<Collaborateur>(this.collaborateurs));

        assertEquals(this.collaborateursLiteDto.size(), actualCollaborateursLiteDto.size());

        for (CollaborateurLiteDto expectedDto : this.collaborateursLiteDto) {
            assertTrue(actualCollaborateursLiteDto.stream().anyMatch(dto -> dto.getId() == expectedDto.getId()));
            assertTrue(actualCollaborateursLiteDto.stream().anyMatch(dto -> dto.getNom() == expectedDto.getNom()));
            assertTrue(actualCollaborateursLiteDto.stream().anyMatch(dto -> dto.getPrenom() == expectedDto.getPrenom()));
            assertTrue(actualCollaborateursLiteDto.stream().anyMatch(dto -> dto.getTelephone() == expectedDto.getTelephone()));
        }
    }

    @Test
    void testToDtoOK() {
        Collaborateur inputCollaborateur = this.collaborateurs.stream()
        .filter(c -> c.getId() == this.id)
        .findFirst()
        .orElseThrow();

        CollaborateurDto expectedCollaborateurDto = this.collaborateursDto.stream()
        .filter(c -> c.getId() == this.id)
        .findFirst()
        .orElseThrow();

        CollaborateurDto actualCollaborateurDto = CollaborateurMapper.toDto(inputCollaborateur);

        assertEquals(expectedCollaborateurDto.getId(), actualCollaborateurDto.getId());
        assertEquals(expectedCollaborateurDto.getNom(), actualCollaborateurDto.getNom());
        assertEquals(expectedCollaborateurDto.getPrenom(), actualCollaborateurDto.getPrenom());
        assertEquals(expectedCollaborateurDto.getAdresse(), actualCollaborateurDto.getAdresse());
        assertEquals(expectedCollaborateurDto.getEmail(), actualCollaborateurDto.getEmail());
        assertEquals(expectedCollaborateurDto.getTelephone(), actualCollaborateurDto.getTelephone());
        assertEquals(expectedCollaborateurDto.getPseudo(), actualCollaborateurDto.getPseudo());
        assertEquals(null, actualCollaborateurDto.getPassword());
        assertEquals(expectedCollaborateurDto.getAdmin(), actualCollaborateurDto.getAdmin());

        // Véhicules
        List<VehiculeLiteDto> expectedVehicules = expectedCollaborateurDto.getVehicules();
        List<VehiculeLiteDto> actualVehicules = actualCollaborateurDto.getVehicules();

        assertEquals(actualVehicules.size(), expectedVehicules.size());

        for (VehiculeLiteDto expectedDto : expectedVehicules) {
            assertTrue(actualVehicules.stream().anyMatch(actualDto -> actualDto.getId() == expectedDto.getId()));
            assertTrue(actualVehicules.stream().anyMatch(actualDto -> actualDto.getMarque() == expectedDto.getMarque()));
            assertTrue(actualVehicules.stream().anyMatch(actualDto -> actualDto.getModele() == expectedDto.getModele()));
        }
    }

    @Test
    void testToDtosOK() {
        Set<CollaborateurDto> actualCollaborateursDto = CollaborateurMapper.toDtos(new HashSet<Collaborateur>(this.collaborateurs));

        assertEquals(this.collaborateursDto.size(), actualCollaborateursDto.size());

        for (CollaborateurDto expectedDto : this.collaborateursDto) {
            assertTrue(actualCollaborateursDto.stream().anyMatch(actualDto -> actualDto.getId() == expectedDto.getId()));
            assertTrue(actualCollaborateursDto.stream().anyMatch(actualDto -> actualDto.getNom() == expectedDto.getNom()));
            assertTrue(actualCollaborateursDto.stream().anyMatch(actualDto -> actualDto.getPrenom() == expectedDto.getPrenom()));
            assertTrue(actualCollaborateursDto.stream().anyMatch(actualDto -> actualDto.getAdresse() == expectedDto.getAdresse()));
            assertTrue(actualCollaborateursDto.stream().anyMatch(actualDto -> actualDto.getEmail() == expectedDto.getEmail()));
            assertTrue(actualCollaborateursDto.stream().anyMatch(actualDto -> actualDto.getTelephone() == expectedDto.getTelephone()));
            assertTrue(actualCollaborateursDto.stream().anyMatch(actualDto -> actualDto.getPseudo() == expectedDto.getPseudo()));
            assertTrue(actualCollaborateursDto.stream().anyMatch(actualDto -> actualDto.getPassword() == null));
            assertTrue(actualCollaborateursDto.stream().anyMatch(actualDto -> actualDto.getAdmin() == expectedDto.getAdmin()));

            List<VehiculeLiteDto> vehicules = expectedDto.getVehicules();

            for (VehiculeLiteDto expectedVehicule : vehicules) {
                assertTrue(actualCollaborateursDto.stream().anyMatch(
                    actualDto -> actualDto.getVehicules().stream().anyMatch(
                        actualVehicule -> actualVehicule.getId() == expectedVehicule.getId()
                    )
                ));
                assertTrue(actualCollaborateursDto.stream().anyMatch(
                    actualDto -> actualDto.getVehicules().stream().anyMatch(
                        actualVehicule -> actualVehicule.getMarque() == expectedVehicule.getMarque()
                    )
                ));
                assertTrue(actualCollaborateursDto.stream().anyMatch(
                    actualDto -> actualDto.getVehicules().stream().anyMatch(
                        actualVehicule -> actualVehicule.getModele() == expectedVehicule.getModele()
                    )
                ));
            }
        }
    }

    @Test
    void testToEntityOK() {
        CollaborateurDto inputCollaborateurDto = this.collaborateursDto.stream()
        .filter(c -> c.getId() == this.id)
        .findFirst()
        .orElseThrow();

        Collaborateur expectedCollaborateur = this.collaborateurs.stream()
        .filter(c -> c.getId() == this.id)
        .findFirst()
        .orElseThrow();

        Collaborateur actualCollaborateur = CollaborateurMapper.toEntity(inputCollaborateurDto);

        assertEquals(expectedCollaborateur.getId(), actualCollaborateur.getId());
        assertEquals(expectedCollaborateur.getNom(), actualCollaborateur.getNom());
        assertEquals(expectedCollaborateur.getPrenom(), actualCollaborateur.getPrenom());
        assertEquals(expectedCollaborateur.getAdresse(), actualCollaborateur.getAdresse());
        assertEquals(expectedCollaborateur.getEmail(), actualCollaborateur.getEmail());
        assertEquals(expectedCollaborateur.getTelephone(), actualCollaborateur.getTelephone());
        assertEquals(expectedCollaborateur.getPseudo(), actualCollaborateur.getPseudo());
        assertEquals(expectedCollaborateur.getPassword(), actualCollaborateur.getPassword());
        assertEquals(expectedCollaborateur.getAdmin(), actualCollaborateur.getAdmin());
        assertEquals(new ArrayList<>(), actualCollaborateur.getVehicules());
    }
}
