import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UserService, Collaborateur } from '../../../../core/auth/user.service';
import { AuthService } from '../../../../core/auth/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  collaborateur?: Collaborateur;
  errorMessage = '';
  // ! = dit à JS que la propriété sera initialisée avant d'être utilisée
  isAuthenticated$!: Observable<boolean>;

  constructor(private userService: UserService, private authService: AuthService) {
  }

  ngOnInit() {
    // Propriété indiquant si l'utilisateur est connecté ou non
    this.isAuthenticated$ = this.authService.isAuthenticated$;

    if (!this.isAuthenticated$) return;

    this.userService.getProfile().subscribe({
      next: (user) => this.collaborateur = user,
      error: (err) => console.error('Non authentifié', err)
    });
  }
}
