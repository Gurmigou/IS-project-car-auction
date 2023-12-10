import {Component, OnInit} from "@angular/core";
import {CarLotInfoForIC} from "../../../assets/model/carAuctionCard";
import {IcLotListService} from "./ic-lot-list.service";

@Component({
  selector: 'app-ic-lot-list',
  templateUrl: './ic-lot-list.component.html',
  styleUrls: ['./ic-lot-list.component.css']
})
export class IcLotListComponent implements OnInit {
  lotsForIC: CarLotInfoForIC[] = [];

  constructor(private icLotListService: IcLotListService) {

  }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.icLotListService.getLotsForIC()
      .subscribe((carLotInfoForIC: CarLotInfoForIC[]) => {
        this.lotsForIC = carLotInfoForIC;
      });
  }
}
