package demo.vroum_vroum.services;

//package demo.vroum_vroum.services;

import demo.vroum_vroum.repositories.VehiculeServiceRepository;
import demo.vroum_vroum.entities.VehiculeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @BeforeEach
    public void setup() {
        vehiculeService = new VehiculeService();
    }

    @Test
    public void testFindAll() {
        List<VehiculeService> list = Arrays.asList(vehiculeService);
        when(vehiculeServiceRepository.findAll()).thenReturn(list);

        List<VehiculeService> result = vehiculeServiceServ.findAll();
        assertEquals(1, result.size());
        verify(vehiculeServiceRepository, times(1)).findAll();
    }
}


