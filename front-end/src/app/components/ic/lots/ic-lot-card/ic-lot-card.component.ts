import {Component, EventEmitter, Input, Output} from "@angular/core";
import {CarLotInfoForIC} from "../../../../assets/model/carAuctionCard";
import {IcLotListService} from "../ic-lot-list.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-ic-lot-card',
  templateUrl: './ic-lot-card.component.html',
  styleUrls: ['./ic-lot-card.component.css']
})
export class IcLotCardComponent {
  @Input() car: CarLotInfoForIC | undefined;
  @Output() reloadEvent = new EventEmitter<void>();

  constructor(private router: Router,
              private icLotListService: IcLotListService) {

  }

  navigateToCarLotUpdatePage() {
    if (this.car) {
      localStorage.setItem('carLotData', JSON.stringify(this.car));
      this.router.navigate(['/new-lot'], {replaceUrl: true});
    }
  }

  deleteLot() {
    this.icLotListService.deleteLot(this.car!.lotId)
      .subscribe(() => this.reloadEvent.emit());
  }
}
