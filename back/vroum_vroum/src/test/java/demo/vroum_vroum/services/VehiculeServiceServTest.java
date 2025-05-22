package demo.vroum_vroum.services;

//package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Collaborateur;
import demo.vroum_vroum.exceptions.Controle;
import demo.vroum_vroum.repositories.VehiculeServiceRepository;
import demo.vroum_vroum.entities.VehiculeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculeServiceServTest {

    @Mock
    private VehiculeServiceRepository vehiculeServiceRepository;

    @InjectMocks
    private VehiculeServiceServ vehiculeServiceServ;

    private VehiculeService vehiculeService;
    private Collaborateur collaborateur;

    @BeforeEach
    public void setup() {
        vehiculeService = new VehiculeService(); // configure ton objet si nécessaire
        collaborateur = new Collaborateur();     // idem ici
    }

    @Test
    public void testFindAll() {
        List<VehiculeService> list = Arrays.asList(vehiculeService);
        when(vehiculeServiceRepository.findAll()).thenReturn(list);

        List<VehiculeService> result = vehiculeServiceServ.findAll();
        assertEquals(1, result.size());
        verify(vehiculeServiceRepository, times(1)).findAll();
    }

//    @Test
//    public void testFindByIdExists() throws Controle {
//        when(vehiculeServiceRepository.existsById(1)).thenReturn(true);
//        when(vehiculeServiceRepository.findById(1)).thenReturn(Optional.of(vehiculeService));
//
//        VehiculeService result = vehiculeServiceServ.findById(1);
//        assertNotNull(result);
//        verify(vehiculeServiceRepository).findById(1);
//    }

//    @Test
//    public void testFindByIdNotExists() {
//        when(vehiculeServiceRepository.existsById(1)).thenReturn(false);
//
//        Controle exception = assertThrows(Controle.class, () -> {
//            vehiculeServiceServ.findById(1);
//        });
//        assertEquals("L'id n'existe pas", exception.getMessage());
//    }
//
//    @Test
//    public void testFindVehiculeServiceByCollaborateur() throws Controle {
//        List<VehiculeService> list = Arrays.asList(vehiculeService);
//        when(vehiculeServiceRepository.findVehiculeServiceByCollaborateurs(collaborateur)).thenReturn(list);
//
//        Iterable<VehiculeService> result = vehiculeServiceServ.findVehiculeServiceByCollaborateur(collaborateur);
//        assertNotNull(result);
//        verify(vehiculeServiceRepository).findVehiculeServiceByCollaborateurs(collaborateur);
//    }
//
//    @Test
//    public void testCreate() throws Controle {
//        vehiculeServiceServ.create(vehiculeService);
//        verify(vehiculeServiceRepository).save(vehiculeService);
//    }
//
//    @Test
//    public void testUpdate() throws Controle {
//        vehiculeServiceServ.update(vehiculeService);
//        verify(vehiculeServiceRepository).save(vehiculeService);
//    }
//
//    @Test
//    public void testDeleteExists() throws Controle {
//        when(vehiculeServiceRepository.existsById(1)).thenReturn(true);
//
//        vehiculeServiceServ.delete(1);
//        verify(vehiculeServiceRepository).deleteById(1);
//    }
//
//    @Test
//    public void testDeleteNotExists() {
//        when(vehiculeServiceRepository.existsById(1)).thenReturn(false);
//
//        Controle exception = assertThrows(Controle.class, () -> {
//            vehiculeServiceServ.delete(1);
//        });
//        assertEquals("L'id n'existe pas.", exception.getMessage());
//    }
}


