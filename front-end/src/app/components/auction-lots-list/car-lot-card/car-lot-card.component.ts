import {Component, Input} from '@angular/core';
import {CarAuctionCard} from "../../../assets/model/carAuctionCard";

@Component({
  selector: 'app-car-lot-card',
  templateUrl: './car-lot-card.component.html',
  styleUrls: ['./car-lot-card.component.css']
})
export class CarLotCardComponent {
  @Input() car: CarAuctionCard | undefined;

  constructor() {
  }
}
