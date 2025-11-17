import { Routes } from '@angular/router';
import { LoginComponent } from './Component/pages/login/login.component';
import { HomeComponent } from './Component/pages/home/home.component';
import { ReservationCovoitComponent } from './Component/pages/reservation_covoit.component/reservation_covoit.component';
import { CovoitOrganises } from './Component/pages/covoit-organises/covoit-organises';
import { CovoitCreer } from './Component/pages/covoit-creer/covoit-creer';
import { authGuard } from './guards/auth.guard';
import {CovoitModifier} from './Component/pages/covoit-modifier/covoit-modifier';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'rechercher-covoit',
    loadComponent: () => import("./Component/pages/recherche_covoit/recherche_covoit.component").then(m => m.RechercheCovoitComponent),
    // Vérifie que l'utilisateur est connecté pour pouvoir accéder à cette route
    canActivate: [authGuard]
  },
  { path: 'mes-reservations',
    component: ReservationCovoitComponent,
    canActivate: [authGuard]
  },
  { path: 'mes-covoits-organises',
    component: CovoitOrganises,
    canActivate: [authGuard]
  },
  { path: 'covoiturages/nouveau',
    component: CovoitCreer,
    canActivate: [authGuard]
  },
  { path: 'covoiturages/modifier/:id',
    component: CovoitModifier,
    canActivate: [authGuard]
  },
  {
    path: '**',
    // page par défaut : page d'accueil
    redirectTo: '/home',
    pathMatch: 'full'
  }
];
