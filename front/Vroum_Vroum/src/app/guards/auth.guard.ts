import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { catchError, map, of } from 'rxjs';
import { AuthService } from '../../core/auth/auth.service';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const isAuthenticated = authService.isAuthenticated$;

  // Vérifie si l'utilisateur est connecté
  if (!isAuthenticated) {
    router.navigateByUrl('/login');
    return false;
  }

  return true;
};
