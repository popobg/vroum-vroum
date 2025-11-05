import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Classe de service construisant des requêtes sécurisées à partir du module HttpClient.
 */
@Injectable({
    providedIn: 'root'
})
export class MyHttpClient {
    // Jeton CSRF à injecter dans le header
    csrfToken: string = "";
    // Adresse du front
    private baseUrl = 'http://localhost:8080'

    constructor(private http: HttpClient) { }

    /**
     * Méthode permettant d'envoyer une requête sécurisée au backend pour récupérer une ressource.
     * @param url url de la requête
     * @param parameters paramètres de requête (optionnel)
     * @returns la ressource demandée ou une erreur
     */
    get(url: string, parameters?: any): any {
        if (!parameters) {
            return this.http.get(`${this.baseUrl}${url}`, { withCredentials: true });
        }
        return this.http.get(`${this.baseUrl}${url}`, { params: parameters, withCredentials: true });
    }

    /**
     * Méthode permettant d'envoyer une requête sécurisée au backend pour créer une ressource.
     * @param url url de la requête
     * @param data corps de requête (peut être un objet vide)
     * @returns retour de la requête (peut être vide - code 204)
     */
    post(url: string, data: any): any {
        return new Observable((subscriber) => {
            this.getCsrf().subscribe((body: any) => {
                this.http.post(
                    `${this.baseUrl}${url}`,
                    data,
                    {
                        headers: new HttpHeaders({
                            "Content-Type": "application/x-www-form-urlencoded",
                            "X-CSRF-TOKEN": body.token
                        }),
                        withCredentials: true
                    }
                ).subscribe({
                    next: (data) => subscriber.next(data),
                    error: (err) => subscriber.error(err),
                    complete: () => subscriber.complete()
                });
            });
        });
    }

    /**
     * Méthode permettant d'envoyer une requête sécurisée au backend pour modifier une ressource existante.
     * @param url url de la requête
     * @param data corps de requête (peut être un objet vide)
     * @returns retour de la requête (peut être vide - code 204)
     */
    put(url: string, data: any): any {
        return this.http.put(
            `${this.baseUrl}${url}`,
            data,
            { headers: new HttpHeaders({ "Content-Type": "application/x-www-form-urlencoded", "X-CSRF-TOKEN": this.csrfToken }), withCredentials: true }
        );
    }

    /**
     * Méthode permettant d'envoyer une requête sécurisée au backend pour supprimer une ressource existante.
     * @param url url de la requête
     * @returns retour de la requête (peut être vide - code 204)
     */
    delete(url: string): any {
        return this.http.delete(`${this.baseUrl}${url}`, { withCredentials: true });
    }

    /**
     * Méthode retournant le token CSRF de la session.
     * @returns token CSRF
     */
    getCsrf() {
        return this.http.get(`${this.baseUrl}/csrf/token`, { withCredentials: true });
    }
}
