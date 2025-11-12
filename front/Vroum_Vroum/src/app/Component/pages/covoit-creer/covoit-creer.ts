import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CovoitService } from '../../../../core/covoit/covoit.service';
import { UserService } from '../../../../core/auth/user.service';
import { CollaborateurLite } from '../../../Model/CollaborateurLite';
import { NgForm, FormsModule } from '@angular/forms';

@Component({
  selector: 'app-covoit-creer',
  templateUrl: './covoit-creer.html',
  standalone: true,
  imports: [FormsModule ],
})
export class CovoitCreer {
  utilisateurConnecte: CollaborateurLite;

  constructor(
    private covoitService: CovoitService,
    private userService: UserService,
    private router: Router
  ) {
    this.utilisateurConnecte = this.userService.getUser()!;
  }

  submitForm(form: NgForm) {
    if (form.invalid) {
      alert('Veuillez remplir tous les champs correctement.');
      return;
    }

    const formValue = form.value;

    const covoitData = {
      date: formValue.date,
      nbPlaces: formValue.nbPlaces,
      distance: formValue.distance,
      duree: formValue.duree,
      adresseDepart: {
        numero: formValue.numeroDepart,
        rue: formValue.rueDepart,
        codePostal: formValue.cpDepart,
        ville: formValue.villeDepart
      },
      adresseArrivee: {
        numero: formValue.numeroArrivee,
        rue: formValue.rueArrivee,
        codePostal: formValue.cpArrivee,
        ville: formValue.villeArrivee
      }
    };

    this.covoitService.creerCovoiturage(this.utilisateurConnecte.id, covoitData).subscribe({
      next: () => {
        alert('Annonce créée avec succès !');
        this.router.navigate(['/covoiturages/organises']);
      },
      error: (err) => {
        console.error(err);
        alert('Erreur lors de la création de l\'annonce');
      }
    });
  }
}
