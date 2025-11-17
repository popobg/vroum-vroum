import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { MyHttpClient } from '../../app/http-client';
import { UserService } from './user.service';

/**
 * Classe de service permettant de gérer l'authentification des utilisateurs à l'application.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: MyHttpClient, private userService: UserService) { }

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

    return this.http.post(`/api/auth/login`, body.toString(), "application/x-www-form-urlencoded").pipe(
      tap({
        next: () => {
          console.log("Authentification réussie !");

          // Récupération de l'utilisateur connecté
          this.userService.getProfile().subscribe({
            next: (data) => {
              // Assignation au userSubject du service user
              this.userService.setUser(data);
            }
          })
        },
        error: () => {
          console.error("Echec d'authentification.");
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

            this.userService.clearUser();
          },
        })
      );
  }
}
