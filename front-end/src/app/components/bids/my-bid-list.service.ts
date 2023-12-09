import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {MyBidCard} from "../../assets/model/carAuctionCard";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MyBidListService {
  constructor(private httpClient: HttpClient) {
  }

  getMyBids(): Observable<MyBidCard[]> {
    return this.httpClient.get<MyBidCard[]>('http://localhost:8080/my-bids/bids-for-user');
  }
}
