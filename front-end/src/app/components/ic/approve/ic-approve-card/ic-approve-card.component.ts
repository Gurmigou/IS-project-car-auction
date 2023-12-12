import {Component, Input} from "@angular/core";
import {CarAuctionApproval} from "../../../../assets/model/carAuctionCard";
import {IcApproveAuctionsService} from "../ic-approve-auctions.service";

@Component({
  selector: 'app-ic-approve-card',
  templateUrl: 'ic-approve-card.component.html',
  styleUrls: ['ic-approve-card.component.css']
})
export class IcApproveCardComponent {
  @Input()
  car: CarAuctionApproval | undefined;

  constructor(private icApproveAuctionsService: IcApproveAuctionsService) {
  }

  onApprove() {
    this.icApproveAuctionsService.setStatus(this.car!.auctionId, 'APPROVED')
      .subscribe(() => {
        this.car!.status = 'APPROVED';
      });
  }

  onReject() {
    this.icApproveAuctionsService.setStatus(this.car!.auctionId, 'REJECTED')
      .subscribe(() => {
        this.car!.status = 'REJECTED';
      });
  }

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
