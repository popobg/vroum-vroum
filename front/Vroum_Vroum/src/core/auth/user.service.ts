import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Collaborateur {
  nom: string;
  prenom: string;
  telephone: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/collaborateur';

  constructor(private http: HttpClient) {}

  login(pseudo: string, password: string): Observable<string> {
    return this.http.post(`${this.baseUrl}/login?pseudo=${pseudo}&password=${password}`, '', { responseType: 'text' });
  }

  getCurrentUser(pseudo: string, password: string): Observable<Collaborateur> {
    const headers = {
      Authorization: 'Basic ' + btoa(`${pseudo}:${password}`)
    };
    return this.http.get<Collaborateur>(`${this.baseUrl}/me`, { headers });
  }
}
