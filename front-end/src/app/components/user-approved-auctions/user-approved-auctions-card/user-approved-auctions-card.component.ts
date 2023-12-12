import {Component, Input} from "@angular/core";
import {CarAuctionApproval} from "../../../assets/model/carAuctionCard";

@Component({
  selector: 'app-user-approved-auctions-card',
  templateUrl: 'user-approved-auctions-card.component.html',
  styleUrls: ['user-approved-auctions-card.component.css']
})
export class UserApprovedAuctionsCardComponent {
  @Input()
  car: CarAuctionApproval | undefined;

  formatStatus(status: "WAITING_FOR_APPROVAL" | "APPROVED" | "REJECTED" | undefined) {
    if (status === 'WAITING_FOR_APPROVAL') {
      return 'Waiting for approval';
    } else if (status === 'APPROVED') {
      return 'Approved';
    } else if (status === 'REJECTED') {
      return 'Rejected';
    } else {
      return 'Unknown';
    }
  }
}
