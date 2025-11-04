import { Routes } from '@angular/router';
import { LoginComponent } from './Component/pages/login/login.component';
import { HomeComponent } from './Component/pages/home/home.component';
import { ReservationCovoitComponent } from './Component/pages/reservation.covoit.component/reservation.covoit.component'

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
    loadComponent: () => import("./Component/pages/recherche_covoit/recherche.covoit.component").then(m => m.RechercheCovoitComponent)
  },
  { path: 'mes-reservations', component: ReservationCovoitComponent },
  // { path: 'mes-covoits-organises', component: MesCovoitsOrganisesComponent },
  {
    path: '**',
    redirectTo: '/login',
    pathMatch: 'full'
  }
];
