import {Component, Input} from "@angular/core";
import {Router} from "@angular/router";
import {MyBidCard} from "../../../assets/model/carAuctionCard";

@Component({
  selector: 'app-my-bid-card',
  templateUrl: './my-bid-card.component.html',
  styleUrls: ['./my-bid-card.component.css']
})
export class MyBidCardComponent {
  @Input() myBid: MyBidCard | undefined;
  private router: Router;

  constructor(router: Router) {
    this.router = router;
  }

  navigateToCarLotDetailedInfo(lotId?: number) {
    if (lotId)
      this.router.navigate(['car-lot', lotId]);
  }
}
