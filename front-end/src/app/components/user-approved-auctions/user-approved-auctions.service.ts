import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CarAuctionApproval} from "../../assets/model/carAuctionCard";

@Injectable({
  providedIn: 'root'
})
export class UserApprovedAuctionsService {
  constructor(private httpClient: HttpClient) {
  }

  getApprovalsForUser(): Observable<CarAuctionApproval[]> {
    return this.httpClient.get<CarAuctionApproval[]>('http://localhost:8080/car-auction/finished-for-user');
  }
}
