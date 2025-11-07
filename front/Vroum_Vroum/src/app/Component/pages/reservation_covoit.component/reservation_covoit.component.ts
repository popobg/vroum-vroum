import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../../core/auth/user.service';
import { CovoitService, Covoiturage } from '../../../../core/covoit/covoit.service';
import { DatePipe, NgIf, NgFor } from '@angular/common';

@Component({
  selector: 'app-reservation-covoit',
  standalone: true,
  imports: [DatePipe, NgIf, NgFor],
  templateUrl: './reservation_covoit.component.html',
  styleUrls: ['./reservation_covoit.component.css']
})
export class Reservation_covoitComponent implements OnInit {
  mesReservations: Covoiturage[] = [];
  utilisateurConnecte: any = null;

  popupVisible = false;
  covoitSelectionne: Covoiturage | null = null;

  constructor(
    private covoitService: CovoitService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.utilisateurConnecte = this.userService.getUtilisateurConnecte();
    if (!this.utilisateurConnecte) {
      console.error('Utilisateur non connecté !');
      return;
    }

    this.chargerReservations();
  }

  chargerReservations(): void {
    this.covoitService.getMesReservations(this.utilisateurConnecte.id).subscribe({
      next: (data) => this.mesReservations = data,
      error: (err) => console.error('Erreur lors du chargement des réservations :', err)
    });
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
    const idReservation = covoit.id;
    const idCollaborateur = this.userService.getUtilisateurConnecte()!.id

    this.covoitService.annulerReservation(idReservation, idCollaborateur).subscribe({
      next: () => {
        alert('Réservation annulée avec succès');
        this.chargerReservations();
        this.fermerPopup()
      },
      error: (err) => {
        console.error('Erreur lors de l’annulation', err);
        alert('Une erreur est survenue');
      }
    });
  }

}
