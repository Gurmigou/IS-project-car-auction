import {Component, Input} from '@angular/core';
import {CarAuctionInfo} from "../../../../assets/model/carAuctionCard";

@Component({
  selector: 'app-car-damaged-auction-lot-information',
  templateUrl: './car-damaged-auction-lot-information.component.html',
  styleUrls: ['./car-damaged-auction-lot-information.component.css']
})
export class CarDamagedAuctionLotInformationComponent {
  @Input() car: CarAuctionInfo | undefined;

  constructor() {
    this.car = {
      carMake: "Infinite",
      carModel: "Q50",
      vin: "1N4BL4BV3LC237897",
      damageDescription: "Front end collision damage",
      carState: "Run and drive",
      initialPrice: 10000,
      currentPrice: 10250,
      auctionStartDate: "2023-10-05",
      timeLeft: "3 days 12 hours",
      auctionDuration: "7 days",
      insuranceCompany: "Geico"
    };
  }
}
