import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {CarDetailedInfo} from "../../../assets/model/carAuctionCard";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CarDamagedAuctionService {
  constructor(private httpClient: HttpClient,
              private httpParams: HttpParams) {
  }

  getLotInfo(lotId: number): Observable<CarDetailedInfo> {
    const params = this.httpParams.set('lotId', lotId.toString());
    return this.httpClient.get<CarDetailedInfo>(
      `http://localhost:8080/car-lot/lot-detailed`, {params});
  }

  submitNewPrice(carAuctionId: number, bidAmount: number): Observable<void> {
    const params = new HttpParams()
      .append('carAuctionId', carAuctionId.toString())
      .append('bidAmount', bidAmount.toString());
    return this.httpClient.post<void>(
      `http://localhost:8080/car-auction/bid`, {}, {params});
  }

  getMaxBidAmount(carAuctionId: number): Observable<number> {
    const params = new HttpParams()
      .append('carAuctionId', carAuctionId.toString());
    return this.httpClient.get<number>(
      `http://localhost:8080/car-auction/max-bid`, {params});
  }
}
