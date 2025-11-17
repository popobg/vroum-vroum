import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CovoitService } from '../../../../core/covoit/covoit.service';
import { Covoiturage } from '../../../Model/Covoiturage';

@Component({
  selector: 'app-edit-covoiturage',
  templateUrl: './covoit-modifier.html',
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./covoit-modifier.css']
})
export class CovoitModifier implements OnInit {

  form!: FormGroup;
  covoiturage!: Covoiturage;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private covoitService: CovoitService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Initialisation du formulaire avec des valeurs par défaut
    this.form = this.fb.group({
      date: ['', Validators.required],
      nbPlaces: [0, [Validators.required, Validators.min(1)]],
      distance: [0, [Validators.required, Validators.min(1)]],
      duree: [0, [Validators.required, Validators.min(1)]],
      adresseDepart: this.fb.group({
        numero: ['', Validators.required],
        rue: ['', Validators.required],
        codePostal: ['', [Validators.required, Validators.pattern('\\d{5}')]],
        nomVille: ['', Validators.required]
      }),
      adresseArrivee: this.fb.group({
        numero: ['', Validators.required],
        rue: ['', Validators.required],
        codePostal: ['', [Validators.required, Validators.pattern('\\d{5}')]],
        nomVille: ['', Validators.required]
      })
    });

    // Récupération du covoiturage existant
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.covoitService.getById(id).subscribe({
      next: (data) => {
        this.covoiturage = data;
        // On patch le formulaire avec les données existantes
        console.log("nombre de place", data.nbPlaces);
        this.form.patchValue({
          date: data.date,
          nbPlaces: data.nbPlaces,
          distance: data.distance,
          duree: data.duree,
          adresseDepart: {
            numero: data.adresseDepart?.numero,
            rue: data.adresseDepart?.rue,
            codePostal: data.adresseDepart?.codePostal,
            nomVille: data.adresseDepart?.nomVille
          },
          adresseArrivee: {
            numero: data.adresseArrivee?.numero,
            rue: data.adresseArrivee?.rue,
            codePostal: data.adresseArrivee?.codePostal,
            nomVille: data.adresseArrivee?.nomVille
          }
        });
      },
      error: () => {
        alert('Erreur : covoiturage introuvable.');
        this.router.navigate(['/covoiturages']);
      }
    });
  }
  annuler(): void {
    this.router.navigateByUrl('/mes-covoits-organises');
  }
  modifier() {
    if (this.form.invalid) return;

    const id = this.covoiturage.id;

    this.covoitService.updateCovoit(id, this.form.value).subscribe({
      next: () => {
        alert('Covoiturage modifié avec succès');
        this.router.navigateByUrl( '/mes-covoits-organises');
      },
      error: () => {
        alert('Erreur lors de la modification');
      }
    });
  }
}
