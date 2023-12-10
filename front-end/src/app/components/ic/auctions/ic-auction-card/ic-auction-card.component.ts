import {Component, Input} from "@angular/core";
import {CarAuctionInfoForIC} from "../../../../assets/model/carAuctionCard";

@Component({
  selector: 'app-ic-auction-card',
  templateUrl: 'ic-auction-card.component.html',
  styleUrls: ['ic-auction-card.component.css']
})
export class IcAuctionCardComponent {
  @Input()
  car: CarAuctionInfoForIC | undefined;
}
