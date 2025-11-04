import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../../core/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {
  loginForm;
  loading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      pseudo: ['', [Validators.required, Validators.minLength(1)]],
      password: ['', [Validators.required, Validators.minLength(1)]]
    });
  }

  onSubmit(): void {
    const { pseudo, password } = this.loginForm.value;
    this.loading = true;

    this.authService.login(pseudo!, password!).subscribe({
      next: () => {
        this.loading = false;

        console.log("Authentification réussie !");

        // Redirection vers la page d'accueil du site
        this.router.navigateByUrl('/home');
      },
      error: (err) => {
        this.loading = false;
        console.error("Erreur d'authentification");
        this.errorMessage = err.error || 'Identifiants incorrects';
      }
    });
  }
}
