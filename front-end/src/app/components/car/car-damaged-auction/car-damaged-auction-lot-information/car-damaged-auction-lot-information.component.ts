import {Component, Input, OnInit} from '@angular/core';
import {CarAuctionInfo} from "../../../../assets/model/carAuctionCard";
import {HttpClient, HttpParams} from "@angular/common/http";
import {FormControl, FormGroup} from "@angular/forms";
import {tap} from "rxjs";

@Component({
  selector: 'app-car-damaged-auction-lot-information',
  templateUrl: './car-damaged-auction-lot-information.component.html',
  styleUrls: ['./car-damaged-auction-lot-information.component.css']
})
export class CarDamagedAuctionLotInformationComponent implements OnInit {
  @Input() carAuction: CarAuctionInfo | undefined;
  formGroup: FormGroup;

  constructor(private httpClient: HttpClient) {
    this.formGroup = new FormGroup({
      bidValue: new FormControl(this.carAuction?.initialPrice)
    });
  }

  ngOnInit(): void {
  }

  submitNewBid() {
    const httpParams = new HttpParams()
      .append("auctionId", this.carAuction?.auctionId.toString() || "")
      .append("bid", this.formGroup.get("bidValue")?.value || "");
    this.httpClient.post<void>("http://localhost:8080/my-bids", {}, {params: httpParams})
      .pipe(
        tap(() => this.getMaxBidForAuction())
      ).subscribe()
  }

  getMaxBidForAuction() {
    const httpParams = new HttpParams()
      .append("auctionId", this.carAuction?.auctionId.toString() || "");
    this.httpClient.get<number>("http://localhost:8080/my-bids/max-bid", {params: httpParams})
      .subscribe((maxBid: number) => {
        this.formGroup.get("bidValue")?.setValue(maxBid)
        this.carAuction!.currentPrice = maxBid;
      })
  }

  auctionStarted(auctionStartDate: string | undefined): boolean {
    return new Date(auctionStartDate || "0") <= new Date();
  }
}
