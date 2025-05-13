package demo.vroum_vroum.service;

import demo.vroum_vroum.repository.CollaborateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollaborateurService {

    @Autowired
    CollaborateurRepository collaborateurRepository;
}
