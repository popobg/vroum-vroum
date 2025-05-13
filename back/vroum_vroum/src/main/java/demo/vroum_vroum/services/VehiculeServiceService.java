package demo.vroum_vroum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.vroum_vroum.repository.VehiculeServiceRepository;

@Service
public class VehiculeServiceService {

    @Autowired
    VehiculeServiceRepository vehiculeServiceRepository;
}
