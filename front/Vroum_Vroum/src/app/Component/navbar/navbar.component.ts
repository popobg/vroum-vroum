import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/auth/auth.service';
import { Observable } from 'rxjs';
import { MyHttpClient } from '../../http-client';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  isAuthenticated$: Observable<boolean>;

  constructor(private authService: AuthService,
      private http: MyHttpClient) {
    this.isAuthenticated$ = this.authService.isAuthenticated$;
  }

  logout() {
    this.authService.logout().subscribe();
  }

}
