import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../../core/auth/auth.service';
import { UserService} from '../../../../core/auth/user.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm;
  loading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      pseudo: ['', [Validators.required, Validators.minLength(1)]],
      password: ['', [Validators.required, Validators.minLength(1)]]
    });
  }

  onSubmit() {
    const { pseudo, password } = this.loginForm.value;

    this.userService.login(pseudo!, password!).subscribe({
      next: () => {
        this.loading = false;
        // stocke le pseudo dans un service ou localStorage
        localStorage.setItem('pseudo', pseudo!);
        localStorage.setItem('password', password!);

        this.router.navigateByUrl('/home');
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err.error || 'Identifiants incorrects';
      }
    });
  }

}
