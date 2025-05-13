package demo.vroum_vroum.service;

import demo.vroum_vroum.entity.Covoiturage;
import demo.vroum_vroum.repository.CovoiturageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CovoiturageService {

    @Autowired
    CovoiturageRepository covoiturage;
}
