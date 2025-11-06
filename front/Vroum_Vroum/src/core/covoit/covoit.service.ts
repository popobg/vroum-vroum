import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

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
  date: string; // ISO string
  adresseDepart: Adresse;
  adresseArrivee: Adresse;
  distance: number;
  duree: number;
  organisateur: CollaborateurLite;
  vehicule: VehiculeLite;
  passagers: CollaborateurLite[];
}

@Injectable({
  providedIn: 'root'
})
export class CovoitService {
  private apiUrl = 'http://localhost:8080/covoiturage';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const pseudo = localStorage.getItem('pseudo') || '';
    const password = localStorage.getItem('password') || '';
    return new HttpHeaders({
      Authorization: 'Basic ' + btoa(`${pseudo}:${password}`)
    });
  }

  // Récupère tous les covoiturages (utilisé pour afficher la liste initiale)
  getTous(): Observable<Covoiturage[]> {
    return this.http.get<Covoiturage[]>(`${this.apiUrl}/tous`, {
      headers: this.getAuthHeaders()
    });
  }

  rechercher(villedep: string, cpdep: string, villearr: string, cparr: string, date: string): Observable<Covoiturage[]> {
    const params = new HttpParams()
      .set('villedep', villedep)
      .set('cpdep', cpdep)
      .set('villearr', villearr)
      .set('cparr', cparr)
      .set('date', date);

    return this.http.get<Covoiturage[]>(`${this.apiUrl}/rechercher`, {
      headers: this.getAuthHeaders(),
      params
    });
  }

  getMesReservations(): Observable<Covoiturage[]> {
    return this.http.get<Covoiturage[]>(`${this.apiUrl}/reservations`, {
      headers: this.getAuthHeaders()
    });
  }

  reserverCovoiturage(idCovoiturage: number, idCollaborateur: number): Observable<void> {
    const url = `${this.apiUrl}/reservations/reserver/${idCovoiturage}/${idCollaborateur}`;
    return this.http.put<void>(url, {}, {headers: this.getAuthHeaders(),withCredentials: true});
  }


  annulerReservation(id: number): Observable<boolean> {
    return this.http.put<boolean>(`${this.apiUrl}/reservations/annuler/${id}`, {}, {
      headers: this.getAuthHeaders()
    });
  }

  getById(id: number): Observable<Covoiturage> {
    return this.http.get<Covoiturage>(`${this.apiUrl}/${id}`, {
      headers: this.getAuthHeaders()
    });
  }
}
