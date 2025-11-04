import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UserService, Collaborateur } from '../../../../core/auth/user.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, HttpClientModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  collaborateur?: Collaborateur;
  errorMessage = '';

  constructor(private userService: UserService) {}

  ngOnInit() {
    const pseudo = localStorage.getItem('pseudo');
    const password = localStorage.getItem('password');
    if (!pseudo || !password) return;

    this.userService.getCurrentUser(pseudo!, password!).subscribe({
      next: (user) => this.collaborateur = user,
      error: (err) => console.error('Non authentifié', err)
    });

  }

}
