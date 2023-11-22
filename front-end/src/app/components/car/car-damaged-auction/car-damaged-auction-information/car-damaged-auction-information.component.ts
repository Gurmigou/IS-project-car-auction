import {Component, Input} from '@angular/core';
import {CarAuctionInfo, CarDetailedInfo, CarLotInfo} from "../../../../assets/model/carAuctionCard";

@Component({
  selector: 'app-car-damaged-auction-information',
  templateUrl: './car-damaged-auction-information.component.html',
  styleUrls: ['./car-damaged-auction-information.component.css']
})
export class CarDamagedAuctionInformationComponent {
  @Input() carLot: CarLotInfo | undefined;
}
