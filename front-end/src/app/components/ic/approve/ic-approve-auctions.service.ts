import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {CarAuctionApproval} from "../../../assets/model/carAuctionCard";

@Injectable({
  providedIn: 'root'
})
export class IcApproveAuctionsService {
  constructor(private httpClient: HttpClient) {
  }

  getApprovalsForIC(): Observable<CarAuctionApproval[]> {
    return this.httpClient.get<CarAuctionApproval[]>('http://localhost:8080/car-auction/finished-for-ic');
  }

  setStatus(auctionId: number, status: string): Observable<any> {
    const params = new HttpParams()
      .append('auctionId', auctionId.toString())
      .append('status', status);
    return this.httpClient.put<any>('http://localhost:8080/car-auction/change-auction-status', {}, {params});
  }
}
