import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../../core/auth/user.service';
import { CovoitService } from '../../../../core/covoit/covoit.service';
import { DatePipe, NgIf, NgFor } from '@angular/common';
import { Covoiturage } from '../../../Model/Covoiturage';
import { CollaborateurLite } from '../../../Model/CollaborateurLite';

@Component({
  selector: 'app-reservation-covoit',
  standalone: true,
  imports: [DatePipe, NgIf, NgFor],
  templateUrl: './reservation.covoit.component.html',
  styleUrls: ['./reservation.covoit.component.css']
})
export class ReservationCovoitComponent implements OnInit {
  mesReservations: Covoiturage[] = [];
  utilisateurConnecte!: CollaborateurLite;

  popupVisible = false;
  covoitSelectionne: Covoiturage | null = null;

  constructor(
    private covoitService: CovoitService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.utilisateurConnecte = this.userService.getUser()!;
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
    this.covoitService.annulerReservation(covoit.id, this.utilisateurConnecte.id).subscribe({
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
