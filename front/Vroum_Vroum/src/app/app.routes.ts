import { Routes } from '@angular/router';
import { LoginComponent } from './Component/pages/login/login.component';
import { HomeComponent } from './Component/pages/home/home.component';
import { Reservation_covoitComponent } from './Component/pages/reservation_covoit.component/reservation_covoit.component';
import { CovoitOrganises } from './Component/pages/covoit-organises/covoit-organises'
export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  {
    path: 'rechercher-covoit',
    loadComponent: () => import("./Component/pages/recherche_covoit/recherche_covoit.component").then(m => m.Recherche_covoitComponent)
  },
  { path: 'mes-reservations', component: Reservation_covoitComponent },
  { path: 'mes-covoits-organises', component: CovoitOrganises },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
