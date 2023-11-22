import {Component, Input} from '@angular/core';
import {CarAuctionCard} from "../../../assets/model/carAuctionCard";
import {Router} from "@angular/router";

@Component({
  selector: 'app-car-lot-card',
  templateUrl: './car-lot-card.component.html',
  styleUrls: ['./car-lot-card.component.css']
})
export class CarLotCardComponent {
  @Input() car: CarAuctionCard | undefined;

  constructor(private router: Router) {
  }

  navigateToCarLotDetailedInfo(lotId?: number) {
    if (lotId)
      this.router.navigate(['car-lot', lotId]);
  }
}
