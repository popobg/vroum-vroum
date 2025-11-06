import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { MyHttpClient } from '../../app/http-client';

export interface CollaborateurLite {
	id: number;
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
	public userSubject = new BehaviorSubject<CollaborateurLite | null>(null);

	constructor(private http: MyHttpClient) { }

	/**
	 * Méthode permettant de récupérer l'utilisateur connecté
	 * @returns un observable de l'objet Collaborateur
	 */
	getProfile(): Observable<CollaborateurLite> {
		return this.http.get(`/collaborateur/me`);
	}

	setUser(user: CollaborateurLite): void {
		this.userSubject.next(user);
	}

	getUser(): CollaborateurLite | null {
		return this.userSubject.value;
	}

	clearUser(): void {
		this.userSubject.next(null);
	}
}
