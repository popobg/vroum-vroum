INSERT INTO `adresse` (`id`, `code_postal`, `numero`, `rue`, `ville`) VALUES (1, '75001', '12', 'Rue de Rivoli', 'Paris');
INSERT INTO `adresse` (`id`, `code_postal`, `numero`, `rue`, `ville`) VALUES (2, '69001', '8', 'Rue de la République', 'Lyon');
INSERT INTO `adresse` (`id`, `code_postal`, `numero`, `rue`, `ville`) VALUES (3, '13001', '5', 'Avenue de la Canebière', 'Marseille');

INSERT INTO `collaborateur` (`admin`, `id`, `telephone`, `adresse`, `email`, `nom`, `password`, `prenom`, `pseudo`) VALUES (b'1', 1, 601020304, '1', 'jean.dupont@example.com', 'Dupont', 'password123', 'Jean', 'jdupont');
INSERT INTO `collaborateur` (`admin`, `id`, `telephone`, `adresse`, `email`, `nom`, `password`, `prenom`, `pseudo`) VALUES (b'0', 2, 605060708, '2', 'marie.martin@example.com', 'Martin', 'securepass', 'Marie', 'mmartin');
INSERT INTO `collaborateur` (`admin`, `id`, `telephone`, `adresse`, `email`, `nom`, `password`, `prenom`, `pseudo`) VALUES (b'1', 3, 608091011, '3', 'pierre.durand@example.com', 'Durand', 'mysecret', 'Pierre', 'pdurand');
