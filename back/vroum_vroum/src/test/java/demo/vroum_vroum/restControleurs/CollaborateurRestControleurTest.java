package demo.vroum_vroum.restControleurs;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.vroum_vroum.dto.CollaborateurDto;
import demo.vroum_vroum.dto.CollaborateurLiteDto;
import demo.vroum_vroum.dto.VehiculeLiteDto;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Vehicule;
import demo.vroum_vroum.mappers.CollaborateurMapper;
import demo.vroum_vroum.mappers.VehiculeMapper;
import demo.vroum_vroum.services.CollaborateurService;
import demo.vroum_vroum.utils.Utils;
import jakarta.persistence.EntityNotFoundException;

/**
 * Classe de tests unitaires du controller de Collaborateur.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CollaborateurRestControleurTest {
    /** Service Collaborateur mocké */
    @MockitoBean
    private CollaborateurService collaborateurService;

    /** Service mock injecté */
    @Autowired
    private MockMvc mock;

    /** mapper injecté */
    @Autowired
    private ObjectMapper objectMapper;

    // DATA
    // Nouvelle entité
    private final Collaborateur newCollaborateur = new Collaborateur("Bernard", "Benoit", "5 avenue du trombone", "benoit.bernard@example.com", "6010203050", "bbernard", "Password4!", false);

    private final CollaborateurDto newCollaborateurDto;

    private final Collaborateur newSavedCollaborateur;

    // Entité existante modifiée
    private final Collaborateur modifiedCollaborateur = new Collaborateur(1, "Blanc", "Xavier", "3 avenue du Maréchal Foch", "xavier.blanc@example.com", "6011121314", "xblanc", "Password11!", false);

    private final CollaborateurDto modifiedCollaborateurDto;

    private final Collaborateur modifiedSavedCollaborateur;

    // Véhicule personnel d'un collaborateur
    private final Vehicule vehicule = new Vehicule(1, 5, "Fiesta", "Ford", "BG069WC");

    // Données de référence
    private final Set<Collaborateur> modelCollaborateurs = Set.of(
        new Collaborateur(1, "Dupont", "Jean", "3 avenue du Général de Gaulle", "jean.dupont@example.com", "6010203040", "jdupont", "Password1!", true),
        new Collaborateur(2, "Martin", "Marie", "12 impasse des Alpes", "marie.martin@example.com", "6050607080", "mmartin", "Password2!", false),
        new Collaborateur(3, "Durant", "Pierre", "5 grande rue des prés", "pierre.durant@example.com", "6090101112", "pdurant", "Password3!", true)
    );

    private final List<Collaborateur> collaborateursMocked = new ArrayList<Collaborateur>();

    private final List<CollaborateurDto> collaborateursDto = new ArrayList<CollaborateurDto>();

    private final List<CollaborateurLiteDto> collaborateursLiteDto =  new ArrayList<CollaborateurLiteDto>();

    /**
     * Constructeur des données
     */
    public CollaborateurRestControleurTest() {
        this.collaborateursMocked.addAll(this.modelCollaborateurs);
        this.collaborateursMocked.get(2).setVehicules(List.of(vehicule));

        this.collaborateursDto.addAll(CollaborateurMapper.toDtos(modelCollaborateurs));
        this.collaborateursDto.get(2).setVehicules(List.of(VehiculeMapper.toDto(vehicule)));

        this.collaborateursLiteDto.addAll(CollaborateurMapper.toLiteDtos(modelCollaborateurs));

        // Construction des entités utiles aux tests de l'ajout d'une nouvelle entité Collaborateur
        this.newCollaborateurDto = CollaborateurMapper.toDto(this.newCollaborateur);
        this.newCollaborateurDto.setPassword(this.newCollaborateur.getPassword());

        this.newSavedCollaborateur = new Collaborateur(4, this.newCollaborateur.getNom(), this.newCollaborateur.getPrenom(), this.newCollaborateur.getAdresse(), this.newCollaborateur.getEmail(), this.newCollaborateur.getTelephone(), this.newCollaborateur.getPseudo(), "Password4!Encoded", this.newCollaborateur.getAdmin());

        // Construction des entités utiles aux tests de la modification d'une entité Collaborateur existante
        this.modifiedCollaborateurDto = CollaborateurMapper.toDto(this.modifiedCollaborateur);
        this.modifiedCollaborateurDto.setPassword(this.modifiedCollaborateur.getPassword());

        this.modifiedSavedCollaborateur = new Collaborateur(this.modifiedCollaborateur.getId(), this.modifiedCollaborateur.getNom(), this.modifiedCollaborateur.getPrenom(), this.modifiedCollaborateur.getAdresse(), this.modifiedCollaborateur.getEmail(), this.modifiedCollaborateur.getTelephone(), this.modifiedCollaborateur.getPseudo(), "Password11!Encoded", this.modifiedCollaborateur.getAdmin());
    }

    @Test
    @WithMockUser(username = "mmartin", password = "Password2!", roles = "USER")
    void testGetAllCollaborateurs_shouldReturnListOfDto() throws Exception {
        // ARRANGE
        when(collaborateurService.getAllCollaborateurs()).thenReturn(new HashSet<>(this.collaborateursMocked));

        // ACT
        this.mock.perform(MockMvcRequestBuilders.get("/collaborateur")).andDo(print())
        // ASSERT
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(this.collaborateursLiteDto.size())))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(Utils.customMap(this.collaborateursLiteDto, CollaborateurLiteDto::getId).toArray())))
                .andExpect(jsonPath("$[*].nom", containsInAnyOrder(Utils.customMap(this.collaborateursLiteDto, CollaborateurLiteDto::getNom).toArray())))
                .andExpect(jsonPath("$[*].prenom", containsInAnyOrder(Utils.customMap(this.collaborateursLiteDto, CollaborateurLiteDto::getPrenom).toArray())))
                .andExpect(jsonPath("$[*].telephone", containsInAnyOrder(Utils.customMap(this.collaborateursLiteDto, CollaborateurLiteDto::getTelephone).toArray())))
                // Vérifie que l'objet retourné est le DTO, sans informations sensibles
                .andExpect(jsonPath("$[*].password").doesNotHaveJsonPath())
                .andExpect(jsonPath("$[*].pseudo").doesNotHaveJsonPath())
                .andExpect(jsonPath("$[*].adresse").doesNotHaveJsonPath())
                .andExpect(jsonPath("$[*].email").doesNotHaveJsonPath());
    }

    @Test
    @WithMockUser(username = "mmartin", password = "Password2!", roles = "USER")
    void testGetAllCollaborateurs_shouldReturnEmptyList() throws Exception {
        when(collaborateurService.getAllCollaborateurs()).thenReturn(new HashSet<>());

        this.mock.perform(MockMvcRequestBuilders.get("/collaborateur")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    void testGetAllCollaborateurs_shouldReturn401_unauthorized() throws Exception {
        this.mock.perform(MockMvcRequestBuilders.get("/collaborateur")).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "jdupont", password = "Password1!", roles = "ADMIN")
    void testGetCollaborateurById_shouldReturnUser_roleAdmin() throws NoSuchElementException, Exception {
        // ARRANGE
        CollaborateurDto collaborateurDto = this.collaborateursDto.stream()
        .filter(c -> c.getId() == 3)
        .findFirst()
        .orElseThrow();

        List<VehiculeLiteDto> vehicules = collaborateurDto.getVehicules();

        Collaborateur collaborateur = this.collaborateursMocked.stream()
        .filter(c -> c.getId() == 3)
        .findFirst()
        .orElseThrow();

        when(collaborateurService.getCollaborateurById(3)).thenReturn(collaborateur);

        // ACT
        this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/3")).andDo(print())
        // ASSERT
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(collaborateurDto.getId())))
                .andExpect(jsonPath("nom", is(collaborateurDto.getNom())))
                .andExpect(jsonPath("prenom", is(collaborateurDto.getPrenom())))
                .andExpect(jsonPath("adresse", is(collaborateurDto.getAdresse())))
                .andExpect(jsonPath("telephone", is(collaborateurDto.getTelephone())))
                .andExpect(jsonPath("email", is(collaborateurDto.getEmail())))
                .andExpect(jsonPath("pseudo", is(collaborateurDto.getPseudo())))
                .andExpect(jsonPath("password", is(collaborateurDto.getPassword())))
                .andExpect(jsonPath("admin", is(collaborateurDto.getAdmin())))
                .andExpect(jsonPath("email", is(collaborateurDto.getEmail())))
                .andExpect(jsonPath("pseudo", is(collaborateurDto.getPseudo())))
                .andExpect(jsonPath("password", is(IsNull.nullValue())))
                .andExpect(jsonPath("vehicules").isArray())
                .andExpect(jsonPath("vehicules.length()", is(vehicules.size())))
                .andExpect(jsonPath("vehicules[*].id", containsInAnyOrder(Utils.customMap(vehicules, VehiculeLiteDto::getId).toArray())))
                .andExpect(jsonPath("vehicules[*].marque", containsInAnyOrder(Utils.customMap(vehicules, VehiculeLiteDto::getMarque).toArray())))
                .andExpect(jsonPath("vehicules[*].modele", containsInAnyOrder(Utils.customMap(vehicules, VehiculeLiteDto::getModele).toArray())));
    }

    @Test
    @WithMockUser(username = "mmartin", password = "Password2!", roles = "USER")
    void testGetCollaborateurById_shouldReturUser_roleUser() throws NoSuchElementException, Exception {
        CollaborateurDto collaborateurDto = this.collaborateursDto.stream()
        .filter(c -> c.getId() == 3)
        .findFirst()
        .orElseThrow();

        List<VehiculeLiteDto> vehicules = collaborateurDto.getVehicules();

        Collaborateur collaborateur = this.collaborateursMocked.stream()
        .filter(c -> c.getId() == 3)
        .findFirst()
        .orElseThrow();

        when(collaborateurService.getCollaborateurById(3)).thenReturn(collaborateur);

        this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/3")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(collaborateurDto.getId())))
                .andExpect(jsonPath("nom", is(collaborateurDto.getNom())))
                .andExpect(jsonPath("prenom", is(collaborateurDto.getPrenom())))
                .andExpect(jsonPath("adresse", is(collaborateurDto.getAdresse())))
                .andExpect(jsonPath("telephone", is(collaborateurDto.getTelephone())))
                .andExpect(jsonPath("email", is(collaborateurDto.getEmail())))
                .andExpect(jsonPath("pseudo", is(collaborateurDto.getPseudo())))
                .andExpect(jsonPath("password", is(collaborateurDto.getPassword())))
                .andExpect(jsonPath("admin", is(collaborateurDto.getAdmin())))
                .andExpect(jsonPath("email", is(collaborateurDto.getEmail())))
                .andExpect(jsonPath("pseudo", is(collaborateurDto.getPseudo())))
                .andExpect(jsonPath("password", is(IsNull.nullValue())))
                .andExpect(jsonPath("vehicules").isArray())
                .andExpect(jsonPath("vehicules.length()", is(vehicules.size())))
                .andExpect(jsonPath("vehicules[*].id", containsInAnyOrder(Utils.customMap(vehicules, VehiculeLiteDto::getId).toArray())))
                .andExpect(jsonPath("vehicules[*].marque", containsInAnyOrder(Utils.customMap(vehicules, VehiculeLiteDto::getMarque).toArray())))
                .andExpect(jsonPath("vehicules[*].modele", containsInAnyOrder(Utils.customMap(vehicules, VehiculeLiteDto::getModele).toArray())));
    }

    @Test
    @WithMockUser(username = "mmartin", password = "Password2!", roles = "USER")
    void testGetCollaborateurById_shouldRetur404_badId() throws Exception {
        int badId = 4;
        String errorMessage = "Pas de collaborateur trouvé à l'ID " + badId;

        when(collaborateurService.getCollaborateurById(badId)).thenThrow(new EntityNotFoundException(errorMessage));

        this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/4")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.Erreur").value("EntityNotFoundException"))
                .andExpect(jsonPath("$.message").value(errorMessage));
    }

    @Test
    @WithMockUser(username = "mmartin", password = "Password2!", roles = "USER")
    void testGetById_shouldReturn400_idIsNotANumber() throws Exception {
        this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/a"))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.Erreur").value("TypeMismatchException"));
    }

    @Test
    void testGetCollaborateurById_shouldReturn401_unauthorized() throws Exception {
        this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/3")).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "mmartin", password = "Password2!", roles = "USER")
    void testGetCurrentUserLite_shouldreturnConnectedUser() throws Exception {
        CollaborateurDto collaborateurDto = this.collaborateursDto.stream()
        .filter(c -> c.getId() == 1)
        .findFirst()
        .orElseThrow();

        Collaborateur collaborateur = this.collaborateursMocked.stream()
        .filter(c -> c.getId() == 1)
        .findFirst()
        .orElseThrow();

        when(collaborateurService.findByPseudo("mmartin")).thenReturn(collaborateur);

        this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/me")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(collaborateurDto.getId())))
                .andExpect(jsonPath("nom", is(collaborateurDto.getNom())))
                .andExpect(jsonPath("prenom", is(collaborateurDto.getPrenom())))
                .andExpect(jsonPath("telephone", is(collaborateurDto.getTelephone())))
                .andExpect(jsonPath("password").doesNotHaveJsonPath())
                .andExpect(jsonPath("pseudo").doesNotHaveJsonPath())
                .andExpect(jsonPath("adresse").doesNotHaveJsonPath())
                .andExpect(jsonPath("email").doesNotHaveJsonPath());
    }

    @Test
    void testGetCurrentUserLite_shouldReturn401_unauthorized() throws Exception {
        this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/me")).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "jdupont", password = "Password1!", roles = "ADMIN")
    void testAddCollaborateur_shouldReturnCreatedCollaborateur() throws Exception {
        when(collaborateurService.createCollaborateur(this.newCollaborateur)).thenReturn(this.newSavedCollaborateur);

        this.mock.perform(MockMvcRequestBuilders.post("/collaborateur")
                    .characterEncoding("UTF-8")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(this.newCollaborateurDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(this.newSavedCollaborateur.getId()))
                .andExpect(jsonPath("$.nom").value(this.newCollaborateurDto.getNom()))
                .andExpect(jsonPath("$.prenom").value(this.newCollaborateurDto.getPrenom()))
                .andExpect(jsonPath("$.telephone").value(this.newCollaborateurDto.getTelephone()))
                .andExpect(jsonPath("$.email").value(this.newCollaborateurDto.getEmail()))
                .andExpect(jsonPath("$.adresse").value(this.newCollaborateurDto.getAdresse()))
                .andExpect(jsonPath("$.pseudo").value(this.newCollaborateurDto.getPseudo()))
                .andExpect(jsonPath("$.password").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.admin").value(this.newCollaborateurDto.getAdmin()))
                .andExpect(jsonPath("vehicules").isArray())
                .andExpect(jsonPath("vehicules.length()", is(0)));
    }

    @Test
    @WithMockUser(username = "mmartin", password = "Password2!", roles = "USER")
    void testAddCollaborateur_shouldReturn400_connectedUserNotAdmin() throws Exception {
        when(collaborateurService.createCollaborateur(this.newCollaborateur)).thenReturn(this.newSavedCollaborateur);

        this.mock.perform(MockMvcRequestBuilders.post("/collaborateur")
                    .characterEncoding("UTF-8")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(this.newCollaborateurDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Access Denied"));
    }

    @Test
    @WithMockUser(username = "jdupont", password = "Password1!", roles = "ADMIN")
    void testAddCollaborateur_shouldReturn403_noCsrf() throws Exception {
        when(collaborateurService.createCollaborateur(this.newCollaborateur)).thenReturn(this.newSavedCollaborateur);

        this.mock.perform(MockMvcRequestBuilders.post("/collaborateur")
                    .characterEncoding("UTF-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(this.newCollaborateurDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testAddCollaborateur_shouldReturn401_unauthorized() throws Exception {
        this.mock.perform(MockMvcRequestBuilders.post("/collaborateur")
                .characterEncoding("UTF-8")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.newCollaborateurDto)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "jdupont", password = "Password1!", roles = "ADMIN")
    void testAddCollaborateur_shouldReturn400_isEmpty() throws Exception {
        CollaborateurDto inputDto = new CollaborateurDto();

        this.mock.perform(MockMvcRequestBuilders.post("/collaborateur")
                .characterEncoding("UTF-8")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "jdupont", password = "Password1!", roles = "ADMIN")
    void testAddCollaborateur_shouldReturn400_containsId() throws Exception {
        CollaborateurDto inputDto = new CollaborateurDto(4, this.newCollaborateurDto.getNom(), this.newCollaborateurDto.getPrenom(), this.newCollaborateurDto.getAdresse(), this.newCollaborateurDto.getEmail(), this.newCollaborateurDto.getTelephone(), this.newCollaborateurDto.getPseudo(), this.newCollaborateurDto.getPassword(), this.newCollaborateurDto.getAdmin());

        Collaborateur mappedCollaborateur = new Collaborateur(4, this.newCollaborateur.getNom(), this.newCollaborateur.getPrenom(), this.newCollaborateur.getAdresse(), this.newCollaborateur.getEmail(), this.newCollaborateur.getTelephone(), this.newCollaborateur.getPseudo(), this.newCollaborateur.getPassword(), this.newCollaborateur.getAdmin());

        String errorMessage = "Un nouveau collaborateur ne peut pas déjà avoir d'ID";

        when(collaborateurService.createCollaborateur(mappedCollaborateur)).thenThrow(new IllegalArgumentException(errorMessage));

        this.mock.perform(MockMvcRequestBuilders.post("/collaborateur")
                .characterEncoding("UTF-8")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.Erreur").value("IllegalArgumentException"))
            .andExpect(jsonPath("$.message").value(errorMessage));
    }

    @Test
    @WithMockUser(username = "jdupont", password = "Password1!", roles = "ADMIN")
    void testAddCollaborateur_shouldReturn400_emptyPassword() throws Exception {
        CollaborateurDto inputDto = new CollaborateurDto(this.newCollaborateurDto.getNom(), this.newCollaborateurDto.getPrenom(), this.newCollaborateurDto.getAdresse(), this.newCollaborateurDto.getEmail(), this.newCollaborateurDto.getTelephone(), this.newCollaborateurDto.getPseudo(), "", this.newCollaborateurDto.getAdmin());

        Collaborateur mappedCollaborateur = new Collaborateur(this.newCollaborateur.getNom(), this.newCollaborateur.getPrenom(), this.newCollaborateur.getAdresse(), this.newCollaborateur.getEmail(), this.newCollaborateur.getTelephone(), this.newCollaborateur.getPseudo(), "", this.newCollaborateur.getAdmin());

        String errorMessage = "Le mot de passe doit contenir au moins 8 caractères, dont au minimum une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial";

        when(collaborateurService.createCollaborateur(mappedCollaborateur)).thenThrow(new IllegalArgumentException(errorMessage));

        this.mock.perform(MockMvcRequestBuilders.post("/collaborateur")
                .characterEncoding("UTF-8")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.Erreur").value("IllegalArgumentException"))
            .andExpect(jsonPath("$.message").value(errorMessage));
    }


    @Test
    @WithMockUser(username = "jdupont", password = "Password1!", roles = "ADMIN")
    void testAddCollaborateur_shouldReturn400_emptyFields() throws Exception {
        CollaborateurDto inputDto = new CollaborateurDto("", "", this.newCollaborateurDto.getAdresse(), "whatever", this.newCollaborateurDto.getTelephone(),"", this.newCollaborateurDto.getPassword(), this.newCollaborateurDto.getAdmin());

        String errorMessagePseudo = "Le collaborateur doit avoir un pseudo";
        String errorMessageEmail = "Le format de l'email n'est pas valide";
        String errorMessageNom = "Le nom doit comporter au moins deux caractères";
        String errorMessagePrenom = "Le prénom doit comporter au moins deux caractères";

        this.mock.perform(MockMvcRequestBuilders.post("/collaborateur")
                .characterEncoding("UTF-8")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.Erreur").value("IllegalArgumentException"))
            .andExpect(jsonPath("$.message", containsString(errorMessagePseudo)))
            .andExpect(jsonPath("$.message", containsString(errorMessageEmail)))
            .andExpect(jsonPath("$.message", containsString(errorMessageNom)))
            .andExpect(jsonPath("$.message", containsString(errorMessagePrenom)));
    }

    @Test
    @WithMockUser(username = "jdupont", password = "Password1!", roles = "ADMIN")
    void testAddCollaborateur_shouldReturn400_invalidNames() throws Exception {
        CollaborateurDto inputDto = new CollaborateurDto("a", "a", this.newCollaborateurDto.getAdresse(), this.newCollaborateurDto.getEmail(), this.newCollaborateurDto.getTelephone(), this.newCollaborateur.getPseudo(), this.newCollaborateurDto.getPassword(), this.newCollaborateurDto.getAdmin());

        String errorMessageNom = "Le nom doit comporter au moins deux caractères";
        String errorMessagePrenom = "Le prénom doit comporter au moins deux caractères";

        this.mock.perform(MockMvcRequestBuilders.post("/collaborateur")
                .characterEncoding("UTF-8")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.Erreur").value("IllegalArgumentException"))
            .andExpect(jsonPath("$.message", containsString(errorMessageNom)))
            .andExpect(jsonPath("$.message", containsString(errorMessagePrenom)));
    }

    @Test
    @WithMockUser(username = "jdupont", password = "Password1!", roles = "ADMIN")
    void testUpdateCollaborateur_shouldReturnCollaborateurModifie_roleAdmin() throws Exception {
        // ARRANGE
        when(collaborateurService.updateCollaborateur(this.modifiedCollaborateur)).thenReturn(this.modifiedSavedCollaborateur);

        // ACT
        this.mock.perform(MockMvcRequestBuilders.put("/collaborateur")
                    .characterEncoding("UTF-8")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(this.modifiedCollaborateurDto)))
        // ASSERT
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(this.modifiedSavedCollaborateur.getId()))
                .andExpect(jsonPath("$.nom").value(this.modifiedSavedCollaborateur.getNom()))
                .andExpect(jsonPath("$.prenom").value(this.modifiedSavedCollaborateur.getPrenom()))
                .andExpect(jsonPath("$.telephone").value(this.modifiedSavedCollaborateur.getTelephone()))
                .andExpect(jsonPath("$.email").value(this.modifiedSavedCollaborateur.getEmail()))
                .andExpect(jsonPath("$.adresse").value(this.modifiedSavedCollaborateur.getAdresse()))
                .andExpect(jsonPath("$.pseudo").value(this.modifiedSavedCollaborateur.getPseudo()))
                .andExpect(jsonPath("$.password").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.admin").value(this.modifiedSavedCollaborateur.getAdmin()))
                .andExpect(jsonPath("vehicules").isArray())
                .andExpect(jsonPath("vehicules.length()", is(0)));
    }

    @Test
    @WithMockUser(username = "mmartin", password = "Password2!", roles = "USER")
    void testUpdateCollaborateur_shouldReturnCollaborateurModifie_roleUser() throws Exception {
        when(collaborateurService.updateCollaborateur(this.modifiedCollaborateur)).thenReturn(this.modifiedSavedCollaborateur);

        this.mock.perform(MockMvcRequestBuilders.put("/collaborateur")
                    .characterEncoding("UTF-8")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(this.modifiedCollaborateurDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(this.modifiedSavedCollaborateur.getId()))
                .andExpect(jsonPath("$.nom").value(this.modifiedSavedCollaborateur.getNom()))
                .andExpect(jsonPath("$.prenom").value(this.modifiedSavedCollaborateur.getPrenom()))
                .andExpect(jsonPath("$.telephone").value(this.modifiedSavedCollaborateur.getTelephone()))
                .andExpect(jsonPath("$.email").value(this.modifiedSavedCollaborateur.getEmail()))
                .andExpect(jsonPath("$.adresse").value(this.modifiedSavedCollaborateur.getAdresse()))
                .andExpect(jsonPath("$.pseudo").value(this.modifiedSavedCollaborateur.getPseudo()))
                .andExpect(jsonPath("$.password").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.admin").value(this.modifiedSavedCollaborateur.getAdmin()))
                .andExpect(jsonPath("vehicules").isArray())
                .andExpect(jsonPath("vehicules.length()", is(0)));
    }

    // @Test
    // void testAddCollaborateur() {

    // }

    // @Test
    // void testDeleteCollaborateur() {

    // }
}
