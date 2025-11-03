import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/collaborateur';

  constructor(private http: HttpClient) {}

  login(pseudo: string, password: string): Observable<string> {
    const params = new HttpParams()
      .set('pseudo', pseudo)
      .set('password', password);

    return this.http.post(this.baseUrl + '/login', null, { params, responseType: 'text' });
  }
}
