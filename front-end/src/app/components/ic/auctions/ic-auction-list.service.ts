import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CarAuctionInfoForIC} from "../../../assets/model/carAuctionCard";

@Injectable({
  providedIn: 'root'
})
export class IcAuctionListService {
  constructor(private httpClient: HttpClient) {
  }

  getAuctionsForIC(): Observable<CarAuctionInfoForIC[]> {
    return this.httpClient.get<CarAuctionInfoForIC[]>('http://localhost:8080/car-auction/active-for-ic');
  }
}
