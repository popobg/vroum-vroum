import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MyHttpClient } from '../../app/http-client';

@Injectable({
  providedIn: 'root'
})
export class VehiculeService {
  private baseUrl = '/api/vehicules';

  constructor(private http: MyHttpClient) {}

  getVehiculesByCollaborateur(collabId: number): Observable<any[]> {
    return this.http.get(`${this.baseUrl}/collaborateur/${collabId}`);
  }

  getVehiculeById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }
}
