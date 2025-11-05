import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MyHttpClient } from '../../app/http-client';

export interface Adresse {
  id: number;
  numero: string;
  rue: string;
  codePostal: string;
  nomVille: string;
}

export interface CollaborateurLite {
  nom: string;
  prenom: string;
  telephone: string;
}

export interface VehiculeLite {
  id: number;
  marque: string;
  modele: string;
}

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

/**
 * Classe de service gérant les méthodes liées au covoiturage
 */
@Injectable({
  providedIn: 'root'
})
export class CovoitService {
  constructor(private http: MyHttpClient) {}

  // Récupère tous les covoiturages (utilisé pour afficher la liste initiale)
  getTous(): Observable<Covoiturage[]> {
    return this.http.get(`/covoiturage/tous`);
  }

  rechercher(villedep: string, cpdep: string, villearr: string, cparr: string, date: string): Observable<Covoiturage[]> {
    const params = new HttpParams()
      .set('villedep', villedep)
      .set('cpdep', cpdep)
      .set('villearr', villearr)
      .set('cparr', cparr)
      .set('date', date);

    return this.http.get(`/covoiturage/rechercher`, params);
  }

  getMesReservations(idCollaborateur: number): Observable<Covoiturage[]> {
    return this.http.get<Covoiturage[]>(`${this.apiUrl}/reservations`, {
      headers: this.getAuthHeaders(),
      params: { idCollaborateur: idCollaborateur.toString() },
      withCredentials: true
    });
  }

  reserverCovoiturage(idCovoiturage: number, idCollaborateur: number): Observable<void> {
    const url = `${this.apiUrl}/reservations/reserver/${idCovoiturage}/${idCollaborateur}`;
    return this.http.put<void>(url, {}, {headers: this.getAuthHeaders(),withCredentials: true});
  }


  annulerReservation(idReservation: number, idCollaborateur: number): Observable<void> {
    const url = `${this.apiUrl}/reservations/annuler/${idReservation}/${idCollaborateur}`;
    return this.http.put<void>(url, {}, { withCredentials: true });
  }

  getById(id: number): Observable<Covoiturage> {
    return this.http.get<Covoiturage>(`${this.apiUrl}/${id}`, {
      headers: this.getAuthHeaders()
    });
  }
}
