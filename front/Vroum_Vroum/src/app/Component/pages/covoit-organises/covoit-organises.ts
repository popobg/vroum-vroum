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
  utilisateurConnecte!: CollaborateurLite

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
}
