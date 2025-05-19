-- Table Adresse
INSERT INTO `adresse` (`id`, `code_postal`, `numero`, `rue`, `ville`) VALUES (1, '75001', '12', 'Rue de Rivoli', 'Paris');
INSERT INTO `adresse` (`id`, `code_postal`, `numero`, `rue`, `ville`) VALUES (2, '69001', '8', 'Rue de la République', 'Lyon');
INSERT INTO `adresse` (`id`, `code_postal`, `numero`, `rue`, `ville`) VALUES (3, '13001', '5', 'Avenue de la Canebière', 'Marseille');

-- Table Collaborateur
INSERT INTO `collaborateur` (`admin`, `id`, `telephone`, `adresse`, `email`, `nom`, `password`, `prenom`, `pseudo`) VALUES (b'1', 1, '601020304', '1', 'jean.dupont@example.com', 'Dupont', 'password123', 'Jean', 'jdupont');
INSERT INTO `collaborateur` (`admin`, `id`, `telephone`, `adresse`, `email`, `nom`, `password`, `prenom`, `pseudo`) VALUES (b'0', 2, '605060708', '2', 'marie.martin@example.com', 'Martin', 'securepass', 'Marie', 'mmartin');
INSERT INTO `collaborateur` (`admin`, `id`, `telephone`, `adresse`, `email`, `nom`, `password`, `prenom`, `pseudo`) VALUES (b'1', 3, '608091011', '3', 'pierre.durand@example.com', 'Durand', 'mysecret', 'Pierre', 'pdurand');
INSERT INTO `collaborateur` (`admin`, `id`, `telephone`, `adresse`, `email`, `nom`, `password`, `prenom`, `pseudo`) VALUES (b'0', 4, '6012131415', '1', 'nom.prenom@example.com', 'Nom', 'password', 'Prenom', 'nprenom');

-- Table Vehicule
INSERT INTO `vehicule` (`id`, `id_collaborateur`, `nb_places`, `immatriculation`, `marque`, `modele`) VALUES (1, 1, 3, 'QJ-333-AT', 'Ford', 'Fiesta');

-- Table Covoiturage
INSERT INTO `covoiturage` (`distance`, `duree`, `id`, `id_adrs_arrivee`, `id_adrs_depart`, `id_collaborateur`, `id_vehicule`, `nb_places`, `date`) VALUES (400, 14400, 1, 2, 1, 1, 1, 2, '2025-05-22 15:04:15.000000');

-- Table Passager_Covoiturage (table de jointure Collaborateur & Covoiturage)
INSERT INTO `passager_covoiturage` (`id_covoiturage`, `id_passager`) VALUES (1, 2);
INSERT INTO `passager_covoiturage` (`id_covoiturage`, `id_passager`) VALUES (1, 3);