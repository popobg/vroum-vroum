package demo.vroum_vroum.services;

import demo.vroum_vroum.entities.Adresse;
import demo.vroum_vroum.repositories.AdresseRepository;
import demo.vroum_vroum.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service concernant l'entité Adresse
 */
@Service
public class AdresseService {
    /** repository de l'entité Adresse */
    private AdresseRepository adresseRepository;

    /**
     * Constructeur
     * @param adresseRepository repository de Adresse
     */
    @Autowired
    public AdresseService(AdresseRepository adresseRepository) {
        this.adresseRepository = adresseRepository;
    }

    /**
     * Méthode de service permettant de récupérer une adresse à partir de son Id
     * @param id Id de l'adresse
     * @return une adresse si elle existe, sinon null
     */
    public Adresse getAdresseById(int id) {
        Optional<Adresse> optAdresse = adresseRepository.findById(id);
        return optAdresse.orElse(null);
    }

    /**
     * Méthode de service permettant de récupérer une adresse à partir de ses informations
     * @param adresse adresse
     * @return une adresse si elle existe, sinon null
     */
    public Adresse getAdresseByVariousFields(Adresse adresse) {
        Optional<Adresse> optAdresse = adresseRepository.findAdresseByNumeroAndRueAndVilleAndCodePostal(adresse.getNumero(), adresse.getRue(), adresse.getVille(), adresse.getCodePostal());
        return optAdresse.orElse(null);
    }

    public Adresse creerAdresse(Adresse adresse) {
        String message = checkChampsAdresse(adresse);

        if (!message.isEmpty()) {
            throw new UnsupportedOperationException(message);
        }

        adresseRepository.save(adresse);
        return adresse;
    }

//    public Adresse updateAdresse(Adresse adresse) {
//        // update adresse
//    }

    /**
     * Méthode privée de vérification des champs de l'objet Adresse
     * @param adresse adresse à vérifier
     * @return message d'erreur ou String vide
     */
    public String checkChampsAdresse(Adresse adresse) {
        if (adresse.getVille().isEmpty()) {
            return "La ville ne peut pas être vide.";
        }

        if (!Validator.matchCodePostalFormat(adresse.getCodePostal())) {
            return "Le code postal doit être composé de 5 nombres.";
        }

        return "";
    }
}
