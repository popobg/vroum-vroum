import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/auth/auth.service';
import { Observable } from 'rxjs';
import { CollaborateurLite, UserService } from '../../../core/auth/user.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  isAuthenticated: Observable<CollaborateurLite | null>;

  constructor(private authService: AuthService, public userService: UserService, private router: Router) {
    this.isAuthenticated = this.userService.userSubject;
  }

  logout() {
    this.authService.logout().subscribe({
      next: () => {
        this.router.navigateByUrl("/home");
      }
    });
  }
}