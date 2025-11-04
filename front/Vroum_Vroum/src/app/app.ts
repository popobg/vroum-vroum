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
  protected readonly title = signal('');

  constructor(private router: Router) {}

  showNavbar(): boolean {
    const hiddenRoutes = ['/login', '/old-login'];
    return !hiddenRoutes.includes(this.router.url);
  }
}
