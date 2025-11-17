import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm, FormsModule } from '@angular/forms';
import { CovoitService } from '../../../../core/covoit/covoit.service';
import { UserService } from '../../../../core/auth/user.service';
import { CollaborateurLite } from '../../../Model/CollaborateurLite';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-covoit-creer',
  templateUrl: './covoit-creer.html',
  standalone: true,
  imports: [FormsModule, CommonModule],
})
export class CovoitCreer {
  utilisateurConnecte: CollaborateurLite;
  vehiculeSelectionneId: number = 1; // ID de véhicule fixe pour test

  constructor(
    private covoitService: CovoitService,
    private userService: UserService,
    private router: Router
  ) {
    this.utilisateurConnecte = this.userService.getUser()!;
  }

  submitForm(form: NgForm) {
    if (form.invalid) {
      alert('Merci de remplir tous les champs.');
      return;
    }

    const f = form.value;

    const covoiturageDto = {
      date: f.date,
      nbPlaces: f.nbPlaces,
      distance: f.distance,
      duree: f.duree,
      adresseDepart: {
        numero: f.numeroDepart,
        rue: f.rueDepart,
        codePostal: f.cpDepart,
        nomVille: f.villeDepart,
      },
      adresseArrivee: {
        numero: f.numeroArrivee,
        rue: f.rueArrivee,
        codePostal: f.cpArrivee,
        nomVille: f.villeArrivee,
      },
      organisateur: { id: this.utilisateurConnecte.id },
      vehicule: { id: this.vehiculeSelectionneId },
    };

    console.log('Envoi DTO JSON au backend :', covoiturageDto);

    // Ici, on envoie en JSON, contentType par défaut dans MyHttpClient sera "application/json"
    this.covoitService.creerCovoiturage(covoiturageDto).subscribe({
      next: () => {
        alert('Covoiturage publié avec succès !');
        this.router.navigate(['/covoiturages']);
      },
      error: (err) => {
        console.error('Erreur lors de la création du covoiturage :', err);
        alert('Une erreur est survenue lors de la création du covoiturage.');
      },
    });
  }


}
