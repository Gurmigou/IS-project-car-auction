import {Component, Input} from '@angular/core';
import {CarAuctionInfo} from "../../../../assets/model/carAuctionCard";

@Component({
  selector: 'app-car-damaged-auction-lot-information',
  templateUrl: './car-damaged-auction-lot-information.component.html',
  styleUrls: ['./car-damaged-auction-lot-information.component.css']
})
export class CarDamagedAuctionLotInformationComponent {
  @Input() carAuction: CarAuctionInfo | undefined;
}
