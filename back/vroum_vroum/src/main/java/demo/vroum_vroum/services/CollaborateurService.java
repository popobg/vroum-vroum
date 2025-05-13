package demo.vroum_vroum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.vroum_vroum.repository.CollaborateurRepository;

@Service
public class CollaborateurService {

    @Autowired
    CollaborateurRepository collaborateurRepository;
}
