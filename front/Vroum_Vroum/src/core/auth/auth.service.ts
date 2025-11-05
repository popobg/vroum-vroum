import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { MyHttpClient } from '../../app/http-client';

/**
 * Classe de service permettant de gérer l'authentification des utilisateurs à l'application.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Statut d'authentification de l'utilisateur
  // true = connecté, false = non connecté
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private http: MyHttpClient) { }

  /**
   * Méthode permettant de se connecter à l'application.
   * Met-à-jour le statut d'authentification du front.
   *
   * @param pseudo identifiant utilisateur
   * @param password mot de passe utilisateur
   * @returns code 200 si OK, code erreur (400, 404 ou 500) en cas d'erreur
   */
  login(pseudo: string, password: string): Observable<any> {
    const body = new URLSearchParams();
    body.set('pseudo', pseudo)
    body.set('password', password);

    return this.http.post(`/api/auth/login`, body.toString()).pipe(
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

  /**
   * Méthode permettant de se déconnecter de l'application.
   * Met-à-jour le statut d'authentification du front.
   *
   * @returns code 200 si OK, code erreur (400, 404 ou 500) en cas d'erreur
   */
  logout(): Observable<any> {
    return this.http.post(`/logout`, {})
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
