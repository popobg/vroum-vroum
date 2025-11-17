import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CovoitService } from '../../../../core/covoit/covoit.service';
import { UserService } from '../../../../core/auth/user.service'
import { Covoiturage } from '../../../Model/Covoiturage';

@Component({
  selector: 'app-recherche-covoit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './recherche_covoit.component.html',
  styleUrls: ['./recherche_covoit.component.css']
})
export class RechercheCovoitComponent {
  /** VARIABLES ET CONSTANTES */
  covoiturages: Covoiturage[] = [];
  filtres = {
    villedep: '',
    cpdep: '',
    villearr: '',
    cparr: '',
    date: ''
  };
  erreurMessage = '';

  popupVisible = false;
  covoiturageSelectionne: any = null;

  /**
   * Constructeur
   * @param covoitService Service lié au covoiturage
   * @param userService Service lié à l'utilisateur
   */
  constructor(private covoitService: CovoitService, private userService: UserService) {}

  ngOnInit(): void {
    // On affiche tous les covoiturages
    this.chargerTous();
  }

  // charge tous les covoiturages au démarrage
  chargerTous(): void {
    this.covoitService.getTous().subscribe({
      next: (data: Covoiturage[]) => {
        this.covoiturages = data;
        this.erreurMessage = data.length ? '' : 'Aucun covoiturage trouvé.';
      },
      error: (err: any) => {
        console.error('Erreur getTous :', err);
        this.erreurMessage = 'Erreur lors du chargement des covoiturages.';
      }
    });
  }

  // recherche filtrée (utilisée par le formulaire)
  rechercher(): void {
    // si tous les filtres sont vides, on recharge tout
    const allEmpty = !this.filtres.villedep && !this.filtres.cpdep && !this.filtres.villearr && !this.filtres.cparr && !this.filtres.date;
    if (allEmpty) {
      this.chargerTous();
      return;
    }

    // date : si l'utilisateur n'a rien saisi, on envoie la date actuelle
    const dateParam = this.filtres.date ? new Date(this.filtres.date).toISOString() : new Date().toISOString();

    this.covoitService.rechercher(
      this.filtres.villedep || '',
      this.filtres.cpdep || '',
      this.filtres.villearr || '',
      this.filtres.cparr || '',
      dateParam
    ).subscribe({
      next: (data: Covoiturage[]) => {
        this.covoiturages = data;
        this.erreurMessage = data.length ? '' : 'Aucun covoiturage trouvé.';
      },
      error: (err: any) => {
        console.error('Erreur rechercher :', err);
        this.erreurMessage = 'Erreur lors de la recherche.';
      }
    });
  }

  ouvrirPopup(covoiturage: any) {
    this.covoiturageSelectionne = covoiturage;
    this.popupVisible = true;
  }

  fermerPopup() {
    this.popupVisible = false;
    this.covoiturageSelectionne = null;
  }

  validerReservation() {
    if (!this.covoiturageSelectionne) return;

    const utilisateur = this.userService.getUser();
    console.log(utilisateur)

    if (!utilisateur) {
      alert('Veuillez vous connecter avant de réserver.');
      return;
    }

    const idCovoiturage = this.covoiturageSelectionne.id;
    const idCollaborateur = utilisateur.id;

    this.covoitService.reserverCovoiturage(idCovoiturage, idCollaborateur).subscribe({
      next: () => {
        alert('Réservation confirmée !');
        this.fermerPopup();
      },
      error: (err) => {
        const message = err.error?.message || 'Erreur lors de la réservation.';
        alert(message);
      }
    });
  }
}
