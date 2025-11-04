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

	constructor(private http: HttpClient) { }

	// get connected user
	getProfile(): Observable<Collaborateur> {
		return this.http.get<Collaborateur>(`${this.baseUrl}/collaborateur/me`, { withCredentials: true });
	}
}
