import {Component, OnDestroy} from '@angular/core';
import {filter, Subscription} from "rxjs";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnDestroy {
  showNavAndFooter: boolean = true;
  private readonly subscription: Subscription;

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {
    this.subscription = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      let activeRoute = this.activatedRoute;
      while (activeRoute.firstChild) {
        activeRoute = activeRoute.firstChild;
      }
      this.showNavAndFooter = !['login', 'register']
        .includes(activeRoute.snapshot.url.join(''));
    });
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
