package demo.vroum_vroum.service;

import demo.vroum_vroum.repository.VehiculeServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculeServiceService {

    @Autowired
    VehiculeServiceRepository vehiculeServiceRepository;
}
