import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MyHttpClient } from '../../app/http-client';
import { Covoiturage } from '../../app/Model/Covoiturage';

/**
 * Classe de service gérant les méthodes liées au covoiturage
 */
@Injectable({
  providedIn: 'root'
})
export class CovoitService {
  baseUrl: string = "/covoiturage";
  reservationUrl: string = this.baseUrl + "/reservations";

  constructor(private http: MyHttpClient) {}

  // Récupère tous les covoiturages (utilisé pour afficher la liste initiale)
  getTous(): Observable<Covoiturage[]> {
    return this.http.get(`${this.baseUrl}/tous`);
  }

  rechercher(villedep: string, cpdep: string, villearr: string, cparr: string, date: string): Observable<Covoiturage[]> {
    const params = new HttpParams()
      .set('villedep', villedep)
      .set('cpdep', cpdep)
      .set('villearr', villearr)
      .set('cparr', cparr)
      .set('date', date);

    return this.http.get(`${this.baseUrl}/rechercher`, params);
  }

  getMesReservations(idCollaborateur: number): Observable<Covoiturage[]> {
    const params = new HttpParams().set('idCollaborateur', idCollaborateur);
    return this.http.get(`${this.reservationUrl}`, params);
  }

  reserverCovoiturage(idCovoiturage: number, idCollaborateur: number): Observable<void> {
    const url = `${this.reservationUrl}/reserver/${idCovoiturage}/${idCollaborateur}`;
    return this.http.put(url, {});
  }

  annulerReservation(idReservation: number, idCollaborateur: number): Observable<void> {
    const url = `${this.reservationUrl}/annuler/${idReservation}/${idCollaborateur}`;
    return this.http.put(url, {});
  }

  getById(id: number): Observable<Covoiturage> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }
}
