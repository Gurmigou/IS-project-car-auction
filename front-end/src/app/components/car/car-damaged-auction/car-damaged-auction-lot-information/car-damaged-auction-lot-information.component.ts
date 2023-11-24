import {Component, Input} from '@angular/core';
import {CarAuctionInfo} from "../../../../assets/model/carAuctionCard";
import {CarDamagedAuctionService} from "../car-damaged-auction.service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-car-damaged-auction-lot-information',
  templateUrl: './car-damaged-auction-lot-information.component.html',
  styleUrls: ['./car-damaged-auction-lot-information.component.css']
})
export class CarDamagedAuctionLotInformationComponent {
  formGroup: FormGroup;
  @Input() carAuction: CarAuctionInfo | undefined;

  constructor(private carDamagedAuctionService: CarDamagedAuctionService) {
    this.formGroup = new FormGroup({
      'newBidAmount': new FormControl(0)
    })
  }

  onSubmitNewPrice() {
    const newBidAmount = this.formGroup.get('newBidAmount')?.value;
    console.log('newBidAmount:', newBidAmount);
    this.carDamagedAuctionService.submitNewPrice(this.carAuction!.auctionId!, newBidAmount)
      .subscribe(() => {
        this.onReload();
      });
  }

  onReload() {
    this.carDamagedAuctionService.getMaxBidAmount(this.carAuction?.auctionId!)
      .subscribe(maxBidAmount => {
        this.carAuction!.currentPrice = maxBidAmount;
      });
  }
}
