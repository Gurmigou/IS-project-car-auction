import {Component, OnInit} from "@angular/core";
import {MyBidCard} from "../../assets/model/carAuctionCard";
import {MyBidListService} from "./my-bid-list.service";

@Component({
  selector: 'app-my-bid-list',
  templateUrl: './my-bid-list.component.html',
  styleUrls: ['./my-bid-list.component.css']
})
export class MyBidListComponent implements OnInit {
  public myBidCards: MyBidCard[] = [];

  constructor(private myBidListService: MyBidListService) {
  }

  ngOnInit(): void {
    this.myBidListService.getMyBids()
      .subscribe((myBidCards: MyBidCard[]) => {
        this.myBidCards = myBidCards;
      });
  }
}
