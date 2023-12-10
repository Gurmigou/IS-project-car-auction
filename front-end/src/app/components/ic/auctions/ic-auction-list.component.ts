import {Component, OnInit} from "@angular/core";
import {CarAuctionInfoForIC} from "../../../assets/model/carAuctionCard";
import {IcAuctionListService} from "./ic-auction-list.service";

@Component({
  selector: 'app-ic-lot-list',
  templateUrl: './ic-auction-list.component.html',
  styleUrls: ['./ic-auction-list.component.css']
})
export class IcAuctionListComponent implements OnInit {
  auctionsForIC: CarAuctionInfoForIC[] = [];

  constructor(private icAuctionListService: IcAuctionListService) {
  }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.icAuctionListService.getAuctionsForIC()
      .subscribe((carAuctionInfoForIC: CarAuctionInfoForIC[]) => {
        this.auctionsForIC = carAuctionInfoForIC;
      });
  }
}
