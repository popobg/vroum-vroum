package demo.vroum_vroum.restControleurs;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import demo.vroum_vroum.dto.CollaborateurLiteDto;
import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.repositories.CollaborateurRepository;
import demo.vroum_vroum.utils.Utils;

@SpringBootTest
@AutoConfigureMockMvc
public class CollaborateurRestControleurTest {
    @MockitoBean
    private CollaborateurRepository collaborateurRepo;

    @Autowired
    private MockMvc mock;

    // DATA
    private final List<Collaborateur> collaborateursMocked = new ArrayList<Collaborateur>(List.of(
        new Collaborateur(1, "Dupont", "Jean", "3 avenue du général de Gaulle", "jean.dupont@example.com", "6010203040", "jdupont", "password123", true),
        new Collaborateur(2, "Martin", "Marie", "12 impasse des Alpes", "marie.martin@example.com", "6050607080", "mmartin", "securepass", false),
        new Collaborateur(3, "Durant", "Pierre", "5 grande rue des prés", "pierre.durant@example.com", "6090101112", "pdurant", "secretpassword", true)
    ));

    private final List<CollaborateurLiteDto> collaborateursDto =  new ArrayList<CollaborateurLiteDto>(List.of(
        new CollaborateurLiteDto(1, "Dupont", "Jean", "6010203040"),
        new CollaborateurLiteDto(2, "Martin", "Marie", "6050607080"),
        new CollaborateurLiteDto(3, "Durant", "Pierre", "6090101112")
    ));

    // @Test
    // void testAddCollaborateur() {

    // }

    // @Test
    // void testDeleteCollaborateur() {

    // }

    @Test
    @WithMockUser(username = "mmartin", password = "securepass", roles = "USER")
    void testGetAllCollaborateurs() throws Exception {
        when(collaborateurRepo.findAllCollaborateurs()).thenReturn(new HashSet<>(this.collaborateursMocked));

        this.mock.perform(MockMvcRequestBuilders.get("/collaborateur")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(this.collaborateursDto.size())))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(Utils.customMap(this.collaborateursDto, CollaborateurLiteDto::getId)
                                                                .toArray())))
                .andExpect(jsonPath("$[*].nom", containsInAnyOrder(Utils.customMap(this.collaborateursDto, CollaborateurLiteDto::getNom)
                                                                .toArray())))
                .andExpect(jsonPath("$[*].prenom", containsInAnyOrder(Utils.customMap(this.collaborateursDto, CollaborateurLiteDto::getPrenom)
                                                                .toArray())))
                .andExpect(jsonPath("$[*].telephone", containsInAnyOrder(Utils.customMap(this.collaborateursDto, CollaborateurLiteDto::getTelephone)
                                                                .toArray())))
                // Vérifie que l'objet retourné est le DTO, sans informations sensibles
                .andExpect(jsonPath("$[*].password").doesNotHaveJsonPath())
                .andExpect(jsonPath("$[*].pseudo").doesNotHaveJsonPath())
                .andExpect(jsonPath("$[*].adresse").doesNotHaveJsonPath())
                .andExpect(jsonPath("$[*].email").doesNotHaveJsonPath());
    }

    // @Test
    // void testGetCollaborateurById() {

    // }

    // @Test
    // @WithMockUser(username = "mmartin", password = "securepass", roles = "USER")
    // void testGetCurrentUserLite() {

    // }

    // @Test
    // void testUpdateCollaborateur() {

    // }
}
