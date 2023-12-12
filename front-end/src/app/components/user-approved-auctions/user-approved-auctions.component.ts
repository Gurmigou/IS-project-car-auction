import {Component, OnInit} from "@angular/core";
import {CarAuctionApproval} from "../../assets/model/carAuctionCard";
import {UserApprovedAuctionsService} from "./user-approved-auctions.service";

@Component({
  selector: 'app-user-approved-auctions',
  templateUrl: 'user-approved-auctions.component.html',
  styleUrls: ['user-approved-auctions.component.css']
})
export class UserApprovedAuctionsComponent implements OnInit {
  carAuctionApprovals: CarAuctionApproval[] = [];

  constructor(private userApprovedAuctionsService: UserApprovedAuctionsService) {
  }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.userApprovedAuctionsService.getApprovalsForUser()
      .subscribe((carAuctionApprovals: CarAuctionApproval[]) => {
        this.carAuctionApprovals = carAuctionApprovals;
      });
  }
}
