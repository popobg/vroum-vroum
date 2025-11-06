import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { UserService } from '../../core/auth/user.service';

export const authGuard: CanActivateFn = () => {
  const userService = inject(UserService);
  const router = inject(Router);

  const isAuthenticated = userService.userSubject.value != null;

  // Vérifie si l'utilisateur est connecté
  if (!isAuthenticated) {
    router.navigateByUrl('/login');
    return false;
  }

  return true;
};
