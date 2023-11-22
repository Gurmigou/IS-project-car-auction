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
}
