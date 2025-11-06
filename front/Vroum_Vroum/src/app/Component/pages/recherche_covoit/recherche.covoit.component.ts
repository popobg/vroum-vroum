import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CovoitService, Covoiturage } from '../../../../core/covoit/covoit.service';
import { UserService } from '../../../../core/auth/user.service'

@Component({
  selector: 'app-recherche-covoit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './recherche.covoit.component.html'
})
export class RechercheCovoitComponent {
  covoiturages: Covoiturage[] = [];
  filtres = {
    villedep: '',
    cpdep: '',
    villearr: '',
    cparr: '',
    date: ''
  };
  erreurMessage = '';

  constructor(
    private covoitService: CovoitService,
    private userService: UserService) {}

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

  // recherche filtrée (utilisé par le formulaire)
  rechercher(): void {
    // si tous les filtres sont vides, on recharge tout
    const allEmpty = !this.filtres.villedep && !this.filtres.cpdep && !this.filtres.villearr && !this.filtres.cparr && !this.filtres.date;
    if (allEmpty) {
      this.chargerTous();
      return;
    }

    // date : si l'utilisateur n'a rien saisi, on envoie une date actuelle
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

  ngOnInit(): void {
    // au démarrage, on affiche tout
    this.chargerTous();
  }

  popupVisible = false;
  covoiturageSelectionne: any = null;

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

    const utilisateur = this.userService.getUtilisateurConnecte();
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
