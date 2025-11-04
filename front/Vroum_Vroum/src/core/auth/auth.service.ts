import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080';

  // Statut d'authentification de l'utilisateur
  // true = connecté, false = non connecté
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private http: HttpClient) { }

  login(pseudo: string, password: string): Observable<any> {
    const body = new URLSearchParams();
    body.set('pseudo', pseudo)
    body.set('password', password);

    return this.http.post(`${this.baseUrl}/api/auth/login`, body.toString(), {
      headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' }),
      withCredentials: true
    }
    ).pipe(
      tap({
        next: () => {
          console.log("Authentification réussie !");
          this.isAuthenticatedSubject.next(true);   // Passe à true si la réponse est OK (200, 204, etc)
        },
        error: () => {
          console.error("Echec d'authentification.");
          this.isAuthenticatedSubject.next(false);
        }
      })
    );
  }

  logout(): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/auth/logout`, {}, { withCredentials: true })
    .pipe(
      tap({
        next: () => {
          console.log("Déconnexion réussie !");
          this.isAuthenticatedSubject.next(false);   // Passe à true si la réponse est OK (200, 204, etc)
        },
        error: err => console.error("Déconnexion échouée : ", err)
      })
    );
  }
}
