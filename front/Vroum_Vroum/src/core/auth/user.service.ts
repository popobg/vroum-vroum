import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MyHttpClient } from '../../app/http-client';

export interface Collaborateur {
	nom: string;
	prenom: string;
	telephone: string;
}

/**
 * Classe de service gérant les requêtes liées à l'utilisateur (ou collaborateur) de l'application.
 */
@Injectable({
	providedIn: 'root'
})
export class UserService {
	constructor(private http: MyHttpClient) { }

	/**
	 * Méthode permettant de récupérer l'utilisateur connecté
	 * @returns un observable de l'objet Collaborateur
	 */
	getProfile(): Observable<Collaborateur> {
		return this.http.get(`/collaborateur/me`);
	}
}
