import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CovoitService } from '../../../../core/covoit/covoit.service';
import { Covoiturage } from '../../../Model/Covoiturage';
import { CollaborateurLite } from '../../../Model/CollaborateurLite';
import { UserService } from '../../../../core/auth/user.service';
import {CommonModule, DatePipe, DecimalPipe} from '@angular/common';

@Component({
  selector: 'app-covoit-organises',
  templateUrl: './covoit-organises.html',
  imports: [
    DatePipe,
    DecimalPipe,
    CommonModule
  ],
  styleUrls: ['./covoit-organises.css']
})
export class CovoitOrganises implements OnInit {
  covoitsOrganises: Covoiturage[] = [];
  utilisateurConnecte!: CollaborateurLite;
  popupVisible = false;
  covoitSelectionne: Covoiturage | null = null;

  constructor(
    private covoitService: CovoitService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.utilisateurConnecte = this.userService.getUser()!;
    this.chargerCovoitsOrganises();
  }

  allerVersCreation(): void {
    this.router.navigate(['/covoiturages/nouveau']);
  }

  chargerCovoitsOrganises(): void {
    this.covoitService.getCovoitOrganises(this.utilisateurConnecte.id).subscribe({
      next: (data) => {
        this.covoitsOrganises = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des covoiturages organisés :', err);
      }
    });
  }

  modifier(id: number) {
    this.router.navigate(['/covoiturages/modifier', id]);
  }

  ouvrirPopup(covoit: Covoiturage): void {
    this.covoitSelectionne = covoit;
    this.popupVisible = true;
  }

  fermerPopup(): void {
    this.popupVisible = false;
    this.covoitSelectionne = null;
  }

  confirmerAnnulation(covoit: any) {
    this.covoitService.supprimerCovoiturage(covoit.id, this.utilisateurConnecte.id).subscribe({
      next: () => {
        alert('Covoiturage supprimé avec succès');
        this.chargerCovoitsOrganises();
        this.fermerPopup()
      },
      error: (err) => {
        console.error('Erreur lors de l’annulation', err);
        alert('Une erreur est survenue');
      }
    });
  }
}
