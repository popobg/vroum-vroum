import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UserService } from '../../../../core/auth/user.service';
import { AuthService } from '../../../../core/auth/auth.service';
import { CollaborateurLite } from '../../../Model/CollaborateurLite';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  collaborateur?: CollaborateurLite | null;
  errorMessage = '';

  constructor(private userService: UserService, private authService: AuthService) {
  }

  ngOnInit() {
    // Surveille les modifications du userSubject et récupère l'utilisateur s'il se connecte
    this.userService.userSubject.subscribe({
      next: (data) => {
        this.collaborateur = data;
      }
    });
  }
}
