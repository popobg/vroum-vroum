import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Classe de service construisant des requêtes sécurisées à partir du module HttpClient.
 */
@Injectable({
    providedIn: 'root'
})
export class MyHttpClient {
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
        return this.http.get(`${this.baseUrl}${url}`, { params: parameters, withCredentials: true });
    }

    /**
     * Méthode permettant d'envoyer une requête sécurisée au backend pour créer une ressource.
     * @param url url de la requête
     * @param data corps de requête (optionnel)
     * @returns retour de la requête (peut être void - code 204)
     */
    post(url: string, data?: any): Observable<any> {
        return this.csrfRequestWrapper((token: string) => {
            return this.http.post(
                `${this.baseUrl}${url}`,
                data,
                {
                    headers: new HttpHeaders({
                        "Content-Type": "application/x-www-form-urlencoded",
                        "X-CSRF-TOKEN": token
                    }),
                    withCredentials: true
                }
            )
        });
    }

    /**
     * Méthode permettant d'envoyer une requête sécurisée au backend pour modifier une ressource existante.
     * @param url url de la requête
     * @param data corps de requête (optionnel)
     * @returns retour de la requête (peut être void - code 204)
     */
    put(url: string, data?: any): Observable<any> {
        return this.csrfRequestWrapper((token: string) => {
            return this.http.put(
                `${this.baseUrl}${url}`,
                data,
                {
                    headers: new HttpHeaders({
                        "Content-Type": "application/x-www-form-urlencoded",
                        "X-CSRF-TOKEN": token
                    }),
                    withCredentials: true
                }
            );
        });
    }

    /**
     * Méthode permettant d'envoyer une requête sécurisée au backend pour supprimer une ressource existante.
     * @param url url de la requête
     * @returns retour de la requête (peut être void - code 204)
     */
    delete(url: string): Observable<any> {
        return this.csrfRequestWrapper((token: string) => {
            return this.http.delete(
                `${this.baseUrl}${url}`,
                {
                    headers: new HttpHeaders({
                        "X-CSRF-TOKEN": token
                    }),
                    withCredentials: true
                }
            );
        });
    }

    /**
     * Wrapper adressant une requête au serveur pour récupérer le token CSRF puis
     * appelant le callback reqWithCsrfToken exécutant une requête avec le token fourni.
     * @returns Observable du type de la ressource retournée par reqWithCsrfToken (peut être void - code http 204)
     */
    private csrfRequestWrapper(reqWithCsrfToken: (csrfToken: string) => Observable<any>) {
        return new Observable<any>((subscriber) => {
            this.http.get(`${this.baseUrl}/csrf/token`, { withCredentials: true }).subscribe((body: any) => {
                reqWithCsrfToken(body.token).subscribe({
                    next: (data) => subscriber.next(data),
                    error: (err) => subscriber.error(err),
                    complete: () => subscriber.complete()
                });
            });
        });
    }
}
