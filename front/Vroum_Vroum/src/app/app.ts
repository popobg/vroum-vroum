import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavbarComponent } from './Component/navbar/navbar.component';
import { CommonModule } from '@angular/common'; // <-- ajouté

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, CommonModule],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  protected readonly title = signal('Vroum_Vroum');

  constructor(private router: Router) {}

  showNavbar(): boolean {
    return this.router.url !== '/login';
  }
}
