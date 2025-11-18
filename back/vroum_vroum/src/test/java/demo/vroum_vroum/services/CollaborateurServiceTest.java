package demo.vroum_vroum.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import demo.vroum_vroum.entities.Adresse;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Covoiturage;
import demo.vroum_vroum.entities.Reservation;
import demo.vroum_vroum.entities.Vehicule;
import demo.vroum_vroum.entities.VehiculeService;
import demo.vroum_vroum.enums.Categorie;
import demo.vroum_vroum.enums.ErrorMessages;
import demo.vroum_vroum.enums.StatutVehicule;
import demo.vroum_vroum.exceptions.NotAuthenticatedException;
import demo.vroum_vroum.repositories.CollaborateurRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Classe de tests unitaires du controller de Collaborateur.
 */
@ExtendWith(MockitoExtension.class)
public class CollaborateurServiceTest {
    @Mock
    private CollaborateurRepository collaborateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CollaborateurService collaborateurService;

    // Suffixe mot de passe hashé
    private static final String encoded = "Encoded";

    // DATA
    private static final int adminId = 1;
    private static final int userId = 2;
    private static final int nonExistingId = 4;
    private static final String adminPseudo = "jdupont";
    private static final String userPseudo = "mmartin";

    private Set<Collaborateur> collaborateurs = new HashSet<>();

    private Collaborateur newCollaborateur;

    @BeforeEach
    void setUp() {
        // Nouveau collaborateur (à ne pas ajouter à la liste "collaborateurs" qui représente les collaborateurs déjà présents en BDD)
        this.newCollaborateur = new Collaborateur("Abitbol", "Georges", "14 avenue Franklin Delano Roosevelt, Paris", "georges.abitbol@example.com", "+12135096996", "gabitbol", "Password4!", false);

        // Collaborateurs
        Collaborateur collaborateur1 = new Collaborateur(1, "Dupont", "Jean", "3 avenue du Général de Gaulle", "jean.dupont@example.com", "6010203040", adminPseudo, "Password1!", true);
        Collaborateur collaborateur2 = new Collaborateur(2, "Martin", "Marie", "12 impasse des Alpes", "marie.martin@example.com", "6050607080", userPseudo, "Password2!", false);
        Collaborateur collaborateur3 = new Collaborateur(3, "Durant", "Pierre", "5 grande rue des prés", "pierre.durant@example.com", "6090101112", "pdurant", "Password3!", true);

        // Véhicules
        Vehicule vehicule1 = new Vehicule(1, 5, "Fiesta", "Ford", "BG069WC");
        Vehicule vehicule2 = new Vehicule(2, 5, "208", "Peugeot", "QT406CB");
        Vehicule vehicule3 = new Vehicule(3, 4, "Zoe", "Renault", "JW733CG");

        // Assignation conducteur - véhicule
        vehicule1.setCollaborateur(collaborateur1);
        vehicule2.setCollaborateur(collaborateur1);
        vehicule3.setCollaborateur(collaborateur3);

        // Association véhicules - collaborateurs
        List<Vehicule> vehicules = new ArrayList<>();
        vehicules.add(vehicule1);
        vehicules.add(vehicule2);

        collaborateur1.setVehicules(vehicules);
        collaborateur3.setVehicules(List.of(vehicule3));

        // Adresses
        Adresse adresse1 = new Adresse(1, "12 bis", "rue Saint-Honoré", "75008", "Paris");
        Adresse adresse2 = new Adresse(2, "4", "chemin du Pitre", "63117", "Chauriat");
        Adresse adresse3 = new Adresse(3, "5", "rue du truc d'Anis", "34270", "Saint-Mathieu-de-Tréviers");

        // Covoiturages
        Covoiturage covoit1 = new Covoiturage(1, 240, 433, 4, adresse1, adresse2, LocalDateTime.of(2026, 2, 15, 11, 15));
        Covoiturage covoit2 = new Covoiturage(2, 210, 350, 2, adresse3, adresse1, LocalDateTime.of(2026,4, 3, 8, 30));
        Covoiturage covoit3 = new Covoiturage(3, 445, 625, 2, adresse3, adresse2, LocalDateTime.of(2026,2, 15, 18, 00));

        // Assignation des collaborateurs organisateurs des covoiturages
        covoit1.setOrganisateur(collaborateur3);
        covoit2.setOrganisateur(collaborateur1);
        covoit3.setOrganisateur(collaborateur1);

        // Association covoiturages - collaborateurs (passagers)
        collaborateur2.setCovoiturages(Set.of(covoit2, covoit3));
        collaborateur3.setCovoiturages(Set.of(covoit1, covoit2, covoit3));

        // Véhicules de service
        VehiculeService vehiculeService1 = new VehiculeService(1, "BM331ET", "Renault", "Kangoo", Categorie.BerlinesTailleL, 0, 30000, 400, 5, StatutVehicule.EnEtat);
        VehiculeService vehiculeService2 = new VehiculeService(2, "BR909WC", "Peugeot", "407", Categorie.BerlinesTailleM, 0, 2000, 200, 5, StatutVehicule.EnEtat);

        // Association véhicules de service - collaborateurs administrateurs du parc automobile
        collaborateur1.setVehiculesService(Set.of(vehiculeService1, vehiculeService2));
        vehiculeService1.setCollaborateurs(Set.of(collaborateur1));
        vehiculeService2.setCollaborateurs(Set.of(collaborateur1));

        // Réservations
        Reservation reservation1 = new Reservation(1, LocalDateTime.of(2025, 12, 20, 12, 00), LocalDateTime.of(2026, 01, 05, 9, 30));
        Reservation reservation2 = new Reservation(2, LocalDateTime.of(2025, 12, 23, 15, 45), LocalDateTime.of(2025, 12, 27, 17, 00));
        Reservation reservation3 = new Reservation(3, LocalDateTime.of(2026, 04, 04, 8, 15), LocalDateTime.of(2026, 06, 30, 11, 00));

        // Association véhicules de service - réservations
        reservation1.setVehiculeService(vehiculeService1);
        reservation2.setVehiculeService(vehiculeService2);
        reservation3.setVehiculeService(vehiculeService1);

        vehiculeService1.setReservations(List.of(reservation1, reservation3));
        vehiculeService2.setReservations(List.of(reservation2));

        // Ajout collaborateurs au set de collaborateurs
        this.collaborateurs.add(collaborateur1);
        this.collaborateurs.add(collaborateur2);
        this.collaborateurs.add(collaborateur3);
    }

    // Nettoie le "contexte" d'authentification de Spring Security
    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    /********************
     *** METHODES GET ***
     ********************/

    @Test
    void findByPseudo_shouldReturnCollaborateur() {
        // ARRANGE
        Collaborateur expectedCollaborateur = this.collaborateurs.stream()
        .filter(c -> c.getPseudo() == adminPseudo)
        .findFirst()
        .orElseThrow();

        when(collaborateurRepository.findByPseudo(adminPseudo)).thenReturn(Optional.of(expectedCollaborateur));

        // ACT
        Collaborateur actualCollaborateur = collaborateurService.findByPseudo(adminPseudo);

        // ASSERT
        assertTrue(actualCollaborateur.equals(expectedCollaborateur));

        List<Vehicule> expectedVehicules = expectedCollaborateur.getVehicules();
        List<Vehicule> actualVehicules = actualCollaborateur.getVehicules();

        for (Vehicule ev : expectedVehicules) {
            assertTrue(actualVehicules.stream().anyMatch(actual -> actual.equals(ev)));
            // Vérifie que le conducteur du véhicule est bien l'utilisateur recherché
            assertTrue(actualVehicules.stream().anyMatch(actual -> actual.getCollaborateur().getId() == expectedCollaborateur.getId()));
        }

        Set<Covoiturage> expectedCovoit = expectedCollaborateur.getCovoiturages();
        Set<Covoiturage> actualCovoit = actualCollaborateur.getCovoiturages();

        for (Covoiturage ec : expectedCovoit) {
            assertTrue(actualCovoit.stream().anyMatch(actual -> actual.equals(ec)));
            assertTrue(actualCovoit.stream().anyMatch(actual -> actual.getOrganisateur().getId() == expectedCollaborateur.getId()));
        }

        List<Reservation> expectedReservations = expectedCollaborateur.getReservations();
        List<Reservation> actualReservations = actualCollaborateur.getReservations();

        for (Reservation er : expectedReservations) {
            assertTrue(actualReservations.stream().anyMatch(actual -> actual.equals(er)));
            assertTrue(actualReservations.stream().anyMatch(actual -> actual.getCollaborateur().getId() == expectedCollaborateur.getId()));

        }

        Set<VehiculeService> expectedVehiculesService = expectedCollaborateur.getVehiculesService();
        Set<VehiculeService> actualVehiculesService = actualCollaborateur.getVehiculesService();

        for (VehiculeService evs : expectedVehiculesService) {
            assertTrue(actualVehiculesService.stream().anyMatch(actual -> actual.equals(evs)));
            assertTrue(actualVehicules.stream().anyMatch(actual -> actual.getCollaborateur().getId() == expectedCollaborateur.getId()));
        }

        // Vérifie que la méthode du repository a bien été appelée
        verify(collaborateurRepository).findByPseudo(adminPseudo);
    }

    @ParameterizedTest
    @ValueSource(strings = {"inconnu"})
    @NullAndEmptySource
    void findByPseudo_shouldThrowUsernameNotFoundException_invalidPseudo(String pseudo) {
        when(collaborateurRepository.findByPseudo(pseudo)).thenReturn(Optional.empty());

        Exception ex = assertThrows(UsernameNotFoundException.class, () -> collaborateurService.findByPseudo(pseudo));
        assertEquals(ErrorMessages.ERROR_MESSAGE_UNKNOWN_USERNAME.toString() + pseudo, ex.getMessage());
    }

    @Test
    void getCurrentUser_shouldReturnCollaborateur() {
        Collaborateur expectedCollaborateur = this.collaborateurs.stream()
        .filter(c -> c.getId() == adminId)
        .findFirst()
        .orElseThrow();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            expectedCollaborateur.getPseudo(),
            expectedCollaborateur.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(collaborateurRepository.findByPseudo(adminPseudo)).thenReturn(Optional.of(expectedCollaborateur));

        Collaborateur actualCollaborateur = collaborateurService.getCurrentUser();

        assertTrue(actualCollaborateur.equals(expectedCollaborateur));
        verify(collaborateurRepository).findByPseudo(adminPseudo);
    }

    @Test
    void getCurrentUser_shouldThrowNotAuthenticatedException() {
        Authentication authentication = mock(AnonymousAuthenticationToken.class);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        Exception ex = assertThrows(NotAuthenticatedException.class, () -> collaborateurService.getCurrentUser());
        assertEquals(ErrorMessages.ERROR_MESSAGE_NO_CONNECTED_USER.toString(), ex.getMessage());
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails_roleAdmin() {
        // ARRANGE
        Collaborateur expectedCollaborateur = this.collaborateurs.stream()
        .filter(c -> c.getPseudo() == adminPseudo)
        .findFirst()
        .orElseThrow();

        when(collaborateurRepository.findByPseudo(adminPseudo)).thenReturn(Optional.of(expectedCollaborateur));

        // ACT
        UserDetails actualUserDetails = collaborateurService.findByPseudo(adminPseudo);

        assertEquals(actualUserDetails.getUsername(), expectedCollaborateur.getPseudo());
        assertEquals(actualUserDetails.getPassword(), expectedCollaborateur.getPassword());
        assertTrue(actualUserDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails_roleUser() {
        // ARRANGE
        Collaborateur expectedCollaborateur = this.collaborateurs.stream()
        .filter(c -> c.getPseudo() == userPseudo)
        .findFirst()
        .orElseThrow();

        when(collaborateurRepository.findByPseudo(userPseudo)).thenReturn(Optional.of(expectedCollaborateur));

        // ACT
        UserDetails actualUserDetails = collaborateurService.loadUserByUsername(userPseudo);

        assertEquals(expectedCollaborateur.getPseudo(), actualUserDetails.getUsername());
        assertEquals(expectedCollaborateur.getPassword(), actualUserDetails.getPassword());
        assertTrue(actualUserDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"inconnu"})
    @NullAndEmptySource
    void loadUserByUsername_shouldThrowUsernameNotFoundException_invalidPseudo(String pseudo) {
        when(collaborateurRepository.findByPseudo(pseudo)).thenReturn(Optional.empty());

        Exception ex = assertThrows(UsernameNotFoundException.class, () -> collaborateurService.loadUserByUsername(pseudo));
        assertEquals(ErrorMessages.ERROR_MESSAGE_UNKNOWN_USERNAME.toString() + pseudo, ex.getMessage());
    }

    @Test
    void testGetAllCollaborateurs_shouldReturnListOfCollaborateurs() throws Exception {
        when(collaborateurRepository.findAllCollaborateurs()).thenReturn(new HashSet<>(this.collaborateurs));

        Set<Collaborateur> actualCollaborateurs = collaborateurService.getAllCollaborateurs();

        for (Collaborateur ec : this.collaborateurs) {
            assertTrue(actualCollaborateurs.stream().anyMatch(actual -> actual.equals(ec)));
        }
    }

    @Test
    void testGetAllCollaborateurs_shouldReturnEmptyList() throws Exception {
        when(collaborateurRepository.findAllCollaborateurs()).thenReturn(new HashSet<>());

        Set<Collaborateur> actualCollaborateurs = collaborateurService.getAllCollaborateurs();

        assertTrue(actualCollaborateurs.isEmpty());
    }

    @Test
    void testGetCollaborateurById_shouldReturnCollaborateur() {
        Collaborateur expectedCollaborateur = this.collaborateurs.stream()
        .filter(c -> c.getId() == adminId)
        .findFirst()
        .orElseThrow();

        when(collaborateurRepository.findById(adminId)).thenReturn(Optional.of(expectedCollaborateur));

        Collaborateur actualCollaborateur = collaborateurService.getCollaborateurById(adminId);

        assertTrue(actualCollaborateur.equals(expectedCollaborateur));
    }

    @ParameterizedTest
    @ValueSource(ints = {nonExistingId, 0})
    void testGetCollaborateurById_shouldThrowEntityNotFoundException(int id) {
        when(collaborateurRepository.findById(id)).thenReturn(Optional.empty());

        Exception ex = assertThrows(EntityNotFoundException.class, () -> collaborateurService.getCollaborateurById(id));
        assertEquals(ErrorMessages.ERROR_MESSAGE_UNKNOWN_ID.toString() + id, ex.getMessage());
    }

    /********************
     ** METHODES POST **
     ********************/

    @Test
    void testCreateCollaborateur_shouldReturnCollaborateur() {
        Collaborateur newSavedCollaborateur = new Collaborateur(nonExistingId, this.newCollaborateur.getNom(), this.newCollaborateur.getPrenom(), this.newCollaborateur.getAdresse(), this.newCollaborateur. getEmail(), this.newCollaborateur.getTelephone(), this.newCollaborateur.getPseudo(), this.newCollaborateur.getPassword(), this.newCollaborateur.getAdmin());

        when(collaborateurRepository.save(this.newCollaborateur)).thenReturn(newSavedCollaborateur);
        //when(passwordEncoder.encode(this.newCollaborateur.getPassword())).thenReturn(this.newCollaborateur.getPassword() + encoded);

        Collaborateur actualCollaborateur = collaborateurService.createCollaborateur(this.newCollaborateur);

        assertEquals(newSavedCollaborateur, actualCollaborateur);
    }

    @ParameterizedTest
    @ValueSource(ints = {adminId, userId})
    void testCreateCollaborateur_shouldThrowIllegalArgumentException(int id) {
        this.newCollaborateur.setId(id);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> collaborateurService.createCollaborateur(newCollaborateur));
        assertEquals(ErrorMessages.ERROR_MESSAGE_ID_FOR_NEW_ITEM.toString(), ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "#", "6", "Ceci est une longue phrase", "Il manque un chiffre !", "il manque 1 majuscule !", "Il manque 1 caractere special"})
    @NullAndEmptySource
    void testCreateCollaborateur_shouldReturnCollaborateur(String password) {
        this.newCollaborateur.setPassword(password);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> collaborateurService.createCollaborateur(newCollaborateur));
        assertEquals(ErrorMessages.ERROR_MESSAGE_PASSWORD.toString(), ex.getMessage());
    }

    /********************
     *** METHODES PUT ***
     ********************/

    @Test
    void testUpdateCollaborateur_shouldReturnCollaborateur_roleAdmin() {
        Collaborateur modifiedCollaborateur = new Collaborateur(adminId, "Nouveau nom", "Nouveau prénom", "Nouvelle adresse", "Nouvel email", "Nouveau numéro", "Nouveau pseudo", "Nouveau mot de passe6!", false);

        Collaborateur expectedCollaborateur = this.collaborateurs.stream()
        .filter(c -> c.getId() == modifiedCollaborateur.getId())
        .findFirst()
        .orElseThrow();

        expectedCollaborateur.setNom(modifiedCollaborateur.getNom());
        expectedCollaborateur.setPrenom(modifiedCollaborateur.getPrenom());
        expectedCollaborateur.setAdresse(modifiedCollaborateur.getAdresse());
        expectedCollaborateur.setEmail(modifiedCollaborateur.getEmail());
        expectedCollaborateur.setTelephone(modifiedCollaborateur.getTelephone());
        expectedCollaborateur.setPseudo(modifiedCollaborateur.getPseudo());
        expectedCollaborateur.setPassword(modifiedCollaborateur.getPassword());
        expectedCollaborateur.setAdmin(modifiedCollaborateur.getAdmin());

        when(collaborateurRepository.existsById(modifiedCollaborateur.getId())).thenReturn(true);
        when(collaborateurRepository.save(modifiedCollaborateur)).thenReturn(expectedCollaborateur);
        // when(passwordEncoder.encode(modifiedCollaborateur.getPassword())).thenReturn(expectedCollaborateur.getPassword());

        Collaborateur actualCollaborateur = collaborateurService.updateCollaborateur(modifiedCollaborateur);

        assertTrue(actualCollaborateur.equals(expectedCollaborateur));
    }

    @Test
    void testUpdateCollaborateur_shouldReturnCollaborateur_roleUser() {
        Collaborateur modifiedCollaborateur = new Collaborateur(userId, "Nouveau nom", "Nouveau prénom", "Nouvelle adresse", "Nouvel email", "Nouveau numéro", "Nouveau pseudo", "Nouveau mot de passe6!", false);

        Collaborateur expectedCollaborateur = this.collaborateurs.stream()
        .filter(c -> c.getId() == modifiedCollaborateur.getId())
        .findFirst()
        .orElseThrow();

        expectedCollaborateur.setNom(modifiedCollaborateur.getNom());
        expectedCollaborateur.setPrenom(modifiedCollaborateur.getPrenom());
        expectedCollaborateur.setAdresse(modifiedCollaborateur.getAdresse());
        expectedCollaborateur.setEmail(modifiedCollaborateur.getEmail());
        expectedCollaborateur.setTelephone(modifiedCollaborateur.getTelephone());
        expectedCollaborateur.setPseudo(modifiedCollaborateur.getPseudo());
        expectedCollaborateur.setPassword(modifiedCollaborateur.getPassword());
        expectedCollaborateur.setAdmin(modifiedCollaborateur.getAdmin());

        when(collaborateurRepository.existsById(modifiedCollaborateur.getId())).thenReturn(true);
        when(collaborateurRepository.save(modifiedCollaborateur)).thenReturn(expectedCollaborateur);
        //when(passwordEncoder.encode(modifiedCollaborateur.getPassword())).thenReturn(expectedCollaborateur.getPassword() + encoded);

        Collaborateur actualCollaborateur = collaborateurService.updateCollaborateur(modifiedCollaborateur);

        assertTrue(actualCollaborateur.equals(expectedCollaborateur));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, nonExistingId})
    void testUpdateCollaborateur_shouldThrowEntityNotFoundException(int id) {
        this.newCollaborateur.setId(id);

        when(collaborateurRepository.existsById(id)).thenReturn(false);

        Exception ex = assertThrows(EntityNotFoundException.class, () -> collaborateurService.updateCollaborateur(this.newCollaborateur));
        assertEquals(ErrorMessages.ERROR_MESSAGE_UNKNOWN_ID.toString() + id, ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "#", "6", "Aa5,", "Ceci est une longue phrase", "Il manque un chiffre !", "il manque 1 majuscule !", "Password66"})
    @NullAndEmptySource
    void testUpdateCollaborateur_shouldReturnCollaborateur(String password) {
        int id = adminId;

        this.newCollaborateur.setId(id);
        this.newCollaborateur.setPassword(password);

        when(collaborateurRepository.existsById(id)).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> collaborateurService.updateCollaborateur(newCollaborateur));
        assertEquals(ErrorMessages.ERROR_MESSAGE_PASSWORD.toString(), ex.getMessage());
    }

    /********************
     * METHODES DELETE *
     ********************/

    @ParameterizedTest
    @ValueSource(ints = {userId, adminId})
    void testDeleteCollaborateur_shouldCallDeletion(int id) {
        when(collaborateurRepository.existsById(id)).thenReturn(true);
        doNothing().when(collaborateurRepository).deleteById(id);

        collaborateurService.deleteCollaborateur(id);
        verify(collaborateurRepository).deleteById(id);
    }

    @ParameterizedTest
    @ValueSource(ints = {nonExistingId, 0})
    void testDeleteCollaborateur_shouldThrowEntityNotFoundException(int id) {
        when(collaborateurRepository.existsById(id)).thenReturn(false);

        Exception ex = assertThrows(EntityNotFoundException.class, () -> collaborateurService.deleteCollaborateur(id));
        assertEquals(ErrorMessages.ERROR_MESSAGE_UNKNOWN_ID.toString() + id, ex.getMessage());
    }
}
