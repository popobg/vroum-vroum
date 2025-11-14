package demo.vroum_vroum.services;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.vroum_vroum.dto.CollaborateurDto;
import demo.vroum_vroum.dto.CollaborateurLiteDto;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.entities.Vehicule;
import demo.vroum_vroum.mappers.CollaborateurMapper;
import demo.vroum_vroum.mappers.VehiculeMapper;
import demo.vroum_vroum.repositories.CollaborateurRepository;
import demo.vroum_vroum.services.CollaborateurService;
import demo.vroum_vroum.utils.Utils;

/**
 * Classe de tests unitaires du controller de Collaborateur.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CollaborateurServiceTest {
    // @MockitoBean
    // private CollaborateurService collaborateurService;

    // @MockitoBean
    // private PasswordEncoder passwordEncoder;

    // @Autowired
    // private MockMvc mock;

    // @Autowired
    // private ObjectMapper objectMapper;

    // // DATA
    // private final Set<Collaborateur> modelCollaborateurs = Set.of(
    //     new Collaborateur(1, "Dupont", "Jean", "3 avenue du général de Gaulle", "jean.dupont@example.com", "6010203040", "jdupont", "password1", true),
    //     new Collaborateur(2, "Martin", "Marie", "12 impasse des Alpes", "marie.martin@example.com", "6050607080", "mmartin", "password2", false),
    //     new Collaborateur(3, "Durant", "Pierre", "5 grande rue des prés", "pierre.durant@example.com", "6090101112", "pdurant", "password3", true)
    // );

    // private final Vehicule vehicule = new Vehicule(1, 5, "Fiesta", "Ford", "BG069WC");

    // private final List<Collaborateur> collaborateursMocked = new ArrayList<Collaborateur>();

    // private final List<CollaborateurDto> collaborateursDto = new ArrayList<CollaborateurDto>();

    // private final List<CollaborateurLiteDto> collaborateursLiteDto =  new ArrayList<CollaborateurLiteDto>();

    // /**
    //  * Constructeur
    //  */
    // public CollaborateurRestControleurTest() {
    //     this.collaborateursMocked.addAll(this.modelCollaborateurs);
    //     this.collaborateursMocked.get(2).setVehicules(Set.of(vehicule));

    //     this.collaborateursDto.addAll(CollaborateurMapper.toDtos(modelCollaborateurs));
    //     this.collaborateursDto.get(2).setVehicules(Set.of(VehiculeMapper.toDto(vehicule)));

    //     this.collaborateursLiteDto.addAll(CollaborateurMapper.toLiteDtos(modelCollaborateurs));
    // }

    // @Test
    // @WithMockUser(username = "mmartin", password = "password2", roles = "USER")
    // void testGetAllCollaborateurs_shouldReturnListOfDto() throws Exception {
    //     when(collaborateurService.getAllCollaborateurs()).thenReturn(new HashSet<>(this.collaborateursMocked));

    //     this.mock.perform(MockMvcRequestBuilders.get("/collaborateur")).andDo(print())
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.length()", is(this.collaborateursLiteDto.size())))
    //             .andExpect(jsonPath("$[*].id", containsInAnyOrder(Utils.customMap(this.collaborateursLiteDto, CollaborateurLiteDto::getId)
    //                                                             .toArray())))
    //             .andExpect(jsonPath("$[*].nom", containsInAnyOrder(Utils.customMap(this.collaborateursLiteDto, CollaborateurLiteDto::getNom)
    //                                                             .toArray())))
    //             .andExpect(jsonPath("$[*].prenom", containsInAnyOrder(Utils.customMap(this.collaborateursLiteDto, CollaborateurLiteDto::getPrenom)
    //                                                             .toArray())))
    //             .andExpect(jsonPath("$[*].telephone", containsInAnyOrder(Utils.customMap(this.collaborateursLiteDto, CollaborateurLiteDto::getTelephone)
    //                                                             .toArray())))
    //             // Vérifie que l'objet retourné est le DTO, sans informations sensibles
    //             .andExpect(jsonPath("$[*].password").doesNotHaveJsonPath())
    //             .andExpect(jsonPath("$[*].pseudo").doesNotHaveJsonPath())
    //             .andExpect(jsonPath("$[*].adresse").doesNotHaveJsonPath())
    //             .andExpect(jsonPath("$[*].email").doesNotHaveJsonPath());
    // }

    // @Test
    // @WithMockUser(username = "mmartin", password = "password2", roles = "USER")
    // void testGetAllCollaborateurs_shouldReturnEmptyList() throws Exception {
    //     when(collaborateurService.getAllCollaborateurs()).thenReturn(new HashSet<>());

    //     this.mock.perform(MockMvcRequestBuilders.get("/collaborateur")).andDo(print())
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.length()", is(0)));
    // }

    // @Test
    // void testGetAllCollaborateurs_shouldReturn401_unauthorized() throws Exception {
    //     this.mock.perform(MockMvcRequestBuilders.get("/collaborateur")).andDo(print())
    //             .andExpect(status().isUnauthorized());
    // }

    // @Test
    // @WithMockUser(username = "jdupont", password = "password1", roles = "ADMIN")
    // void testGetCollaborateurById_shouldReturnUser_roleAdmin() throws Exception {
    //     when(collaborateurService.getCollaborateurById(3)).thenReturn(this.collaborateursMocked.get(2));

    //     this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/3")).andDo(print())
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("id", is(this.collaborateursMocked.get(2).getId())))
    //             .andExpect(jsonPath("nom", is(this.collaborateursMocked.get(2).getNom())))
    //             .andExpect(jsonPath("prenom", is(this.collaborateursMocked.get(2).getPrenom())))
    //             .andExpect(jsonPath("adresse", is(this.collaborateursMocked.get(2).getAdresse())))
    //             .andExpect(jsonPath("telephone", is(this.collaborateursMocked.get(2).getTelephone())))
    //             .andExpect(jsonPath("email", is(this.collaborateursMocked.get(2).getEmail())))
    //             .andExpect(jsonPath("pseudo", is(this.collaborateursMocked.get(2).getPseudo())))
    //             .andExpect(jsonPath("password", is(this.collaborateursMocked.get(2).getPassword())))
    //             .andExpect(jsonPath("admin", is(this.collaborateursMocked.get(2).getAdmin())));
    // }

    // @Test
    // @WithMockUser(username = "mmartin", password = "password2", roles = "USER")
    // void testGetCollaborateurById_shouldReturUser_roleUser() throws Exception {
    //     when(collaborateurService.findById(3)).thenReturn(Optional.of(this.collaborateursMocked.get(2)));

    //     this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/3")).andDo(print())
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("id", is(this.collaborateursMocked.get(2).getId())))
    //             .andExpect(jsonPath("nom", is(this.collaborateursMocked.get(2).getNom())))
    //             .andExpect(jsonPath("prenom", is(this.collaborateursMocked.get(2).getPrenom())))
    //             .andExpect(jsonPath("adresse", is(this.collaborateursMocked.get(2).getAdresse())))
    //             .andExpect(jsonPath("telephone", is(this.collaborateursMocked.get(2).getTelephone())))
    //             .andExpect(jsonPath("email", is(this.collaborateursMocked.get(2).getEmail())))
    //             .andExpect(jsonPath("pseudo", is(this.collaborateursMocked.get(2).getPseudo())))
    //             .andExpect(jsonPath("password", is(this.collaborateursMocked.get(2).getPassword())))
    //             .andExpect(jsonPath("admin", is(this.collaborateursMocked.get(2).getAdmin())));
    // }

    // @Test
    // @WithMockUser(username = "mmartin", password = "password2", roles = "USER")
    // void testGetCollaborateurById_shouldRetur404_badId() throws Exception {
    //     when(collaborateurService.findById(4)).thenReturn(Optional.empty());

    //     this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/4")).andDo(print())
    //             .andExpect(status().isNotFound());
    // }

    // @Test
    // @WithMockUser(username = "mmartin", password = "password2", roles = "USER")
    // void testGetById_shouldReturn400_idIsNotANumber() throws Exception {
    //     this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/a"))
    //            .andExpect(status().isBadRequest());
    // }

    // @Test
    // void testGetCollaborateurById_shouldReturn401_unauthorized() throws Exception {
    //     this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/3")).andDo(print())
    //             .andExpect(status().isUnauthorized());
    // }

    // @Test
    // @WithMockUser(username = "mmartin", password = "password2", roles = "USER")
    // void testGetCurrentUserLite_shouldreturnConnectedUser() throws Exception {
    //     when(collaborateurService.findByPseudo("mmartin")).thenReturn(Optional.of(this.collaborateursMocked.get(1)));

    //     this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/me")).andDo(print())
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("id", is(this.collaborateursMocked.get(1).getId())))
    //             .andExpect(jsonPath("nom", is(this.collaborateursLiteDto.get(1).getNom())))
    //             .andExpect(jsonPath("prenom", is(this.collaborateursLiteDto.get(1).getPrenom())))
    //             .andExpect(jsonPath("telephone", is(this.collaborateursLiteDto.get(1).getTelephone())))
    //             .andExpect(jsonPath("password").doesNotHaveJsonPath())
    //             .andExpect(jsonPath("pseudo").doesNotHaveJsonPath())
    //             .andExpect(jsonPath("adresse").doesNotHaveJsonPath())
    //             .andExpect(jsonPath("email").doesNotHaveJsonPath());
    // }

    // @Test
    // void testGetCurrentUserLite_shouldReturn401_unauthorized() throws Exception {
    //     this.mock.perform(MockMvcRequestBuilders.get("/collaborateur/me")).andDo(print())
    //             .andExpect(status().isUnauthorized());
    // }

    //  @Test
    // @WithMockUser(username = "jdupont", password = "password1", roles = "ADMIN")
    // void testAddCollaborateur_shouldReturnCreatedCollaborateur() throws Exception {
    //     // Given
    //     CollaborateurDto inputDto = new CollaborateurDto("Dupont", "Jean", "3 avenue du général de Gaulle", "jean.dupont@example.com", "6010203040", "jdupont", "password1", false);

    //     Collaborateur mappedInput = new Collaborateur("Dupont", "Jean", "3 avenue du général de Gaulle", "jean.dupont@example.com", "6010203040", "jdupont", "encodedPass", false);

    //     Collaborateur savedEntity = new Collaborateur(1, "Dupont", "Jean", "3 avenue du général de Gaulle", "jean.dupont@example.com", "6010203040", "jdupont", "encodedPass", false);

    //     when(passwordEncoder.encode("password1")).thenReturn("encodedPass");
    //     when(collaborateurService.save(mappedInput)).thenReturn(savedEntity);

    //     this.mock.perform(MockMvcRequestBuilders.post("/collaborateur")
    //             .with(csrf())
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .accept(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(inputDto)))
    //         .andExpect(status().isOk())
    //         .andExpect(jsonPath("$.id").value(1))
    //         .andExpect(jsonPath("$.nom").value("Dupont"))
    //         .andExpect(jsonPath("$.prenom").value("Jean"))
    //         .andExpect(jsonPath("$.telephone").value("6010203040"));

    //     // Vérification de l'encodage du mot de passe
    //     verify(passwordEncoder).encode("password1");
    // }

    // @Test
    // void testUpdateCollaborateur() {

    // }

    // @Test
    // void testAddCollaborateur() {

    // }

    // @Test
    // void testDeleteCollaborateur() {

    // }
}
