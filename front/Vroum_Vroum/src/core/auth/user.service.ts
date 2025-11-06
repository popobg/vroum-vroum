import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Collaborateur {
  id: number;
  nom: string;
  prenom: string;
  telephone: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/collaborateur';
  private utilisateurConnecte: Collaborateur | null = null;

  constructor(private http: HttpClient) {
    const storedUser = localStorage.getItem('utilisateurConnecte');
    if (storedUser) {
      this.utilisateurConnecte = JSON.parse(storedUser);
    }
  }

  login(pseudo: string, password: string): Observable<string> {
    return this.http.post(`${this.baseUrl}/login?pseudo=${pseudo}&password=${password}`, '', { responseType: 'text' });
  }

  getCurrentUser(pseudo: string, password: string): Observable<Collaborateur> {
    const headers = {
      Authorization: 'Basic ' + btoa(`${pseudo}:${password}`)
    };
    return this.http.get<Collaborateur>(`${this.baseUrl}/me`, { headers });
  }

  /**
   * Retourne l’utilisateur connecté depuis la mémoire locale.
   */
  getUtilisateurConnecte(): Collaborateur | null {
    if (!this.utilisateurConnecte) {
      const storedUser = localStorage.getItem('utilisateurConnecte');
      if (storedUser) {
        this.utilisateurConnecte = JSON.parse(storedUser);
      }
    }
    return this.utilisateurConnecte;
  }

  /**
   * Sauvegarde un utilisateur après login
   */
  setUtilisateurConnecte(utilisateur: Collaborateur): void {
    this.utilisateurConnecte = utilisateur;
    localStorage.setItem('utilisateurConnecte', JSON.stringify(utilisateur));
  }

  /**
   * Déconnecte complètement
   */
  logout(): void {
    this.utilisateurConnecte = null;
    localStorage.removeItem('utilisateurConnecte');
  }
}
