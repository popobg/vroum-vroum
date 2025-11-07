import { Adresse } from "./Adresse";
import { CollaborateurLite } from "./CollaborateurLite";
import { VehiculeLite } from "./VehiculeLite";

export interface Covoiturage {
  id: number;
  date: string;
  adresseDepart: Adresse;
  adresseArrivee: Adresse;
  nbPlaces: number;
  distance: number;
  duree: number;
  organisateur: CollaborateurLite;
  vehicule: VehiculeLite;
  passagers: CollaborateurLite[];
}