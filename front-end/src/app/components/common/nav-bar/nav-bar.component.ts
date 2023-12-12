import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent {
  protected readonly localStorage = localStorage;

  constructor(private router: Router) {
  }

  onLogout() {
    localStorage.removeItem('token');
    localStorage.removeItem('type');
    this.router.navigate(['/login'], {replaceUrl: true});
  }
}
