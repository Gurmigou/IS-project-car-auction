import {Component, OnInit} from "@angular/core";
import {IcApproveAuctionsService} from "./ic-approve-auctions.service";
import {CarAuctionApproval} from "../../../assets/model/carAuctionCard";

@Component({
  selector: 'app-ic-approve-auctions',
  templateUrl: 'ic-approve-auctions.component.html',
  styleUrls: ['ic-approve-auctions.component.css']
})
export class IcApproveAuctionsComponent implements OnInit {
  carAuctionApprovals: CarAuctionApproval[] = [];

  constructor(private userApprovedAuctionsService: IcApproveAuctionsService) {
  }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.userApprovedAuctionsService.getApprovalsForIC()
      .subscribe((carAuctionApprovals: CarAuctionApproval[]) => {
        this.carAuctionApprovals = carAuctionApprovals;
      });
  }
}
