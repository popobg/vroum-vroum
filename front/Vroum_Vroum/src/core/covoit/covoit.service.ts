import { Injectable } from '@angular/core';
import { HttpParams, HttpClient  } from '@angular/common/http';
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

  constructor(private http: MyHttpClient, private httpClient: HttpClient) {}

  // Récupère tous les covoiturages (utilisé pour afficher la liste initiale)
  getTous(): Observable<Covoiturage[]> {
    return this.http.get(`${this.baseUrl}/tous`);
  }

  getCovoitOrganises(idCollaborateur: number): Observable<Covoiturage[]> {
    const params = new HttpParams().set('idCollaborateur', idCollaborateur);
    return this.http.get(`${this.baseUrl}/organises`, params);
  }

  creerCovoiturage(covoiturageDto: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/creer`, covoiturageDto);
  }

  supprimerCovoiturage(idCovoit: number, idCollaborateur: number): Observable<void> {
    const url = `${this.baseUrl}/delete/${idCovoit}/${idCollaborateur}`;
    return this.http.delete(url);
  }

  /**
   * Met à jour un covoiturage existant
   * @param id Id du covoiturage
   * @param covoiturage données modifiées
   */
  updateCovoit(id: number, covoiturage: Covoiturage): Observable<void> {
    return this.http.put(`${this.baseUrl}/update/${id}`, covoiturage);
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
