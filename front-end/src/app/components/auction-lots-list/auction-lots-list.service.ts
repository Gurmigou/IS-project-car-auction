import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {CarAuctionCard} from "../../assets/model/carAuctionCard";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuctionLotsListService {
  constructor(private httpClient: HttpClient) {
  }

  getAuctionLots(): Observable<CarAuctionCard[]> {
    return this.httpClient.get<CarAuctionCard[]>('http://localhost:8080/car-auction/active-for-user');
  }
}
