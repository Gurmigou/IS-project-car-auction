import {Component, OnInit} from '@angular/core';
import {CarAuctionCard} from "../../assets/model/carAuctionCard";
import {AuctionLotsListService} from "./auction-lots-list.service";

@Component({
  selector: 'app-auction-lots-list',
  templateUrl: './auction-lots-list.component.html',
  styleUrls: ['./auction-lots-list.component.css']
})
export class AuctionLotsListComponent implements OnInit {
  auctionCars: CarAuctionCard[] = [];

  constructor(private auctionLotsListService: AuctionLotsListService) {

  }

  ngOnInit(): void {
    this.auctionLotsListService.getAuctionLots()
      .subscribe((auctionCars: CarAuctionCard[]) => {
        this.auctionCars = auctionCars;
      });
  }
}
