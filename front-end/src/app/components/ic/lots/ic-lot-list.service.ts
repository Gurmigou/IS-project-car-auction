import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {CarLotInfoForIC} from "../../../assets/model/carAuctionCard";

@Injectable({
  providedIn: 'root'
})
export class IcLotListService {
  constructor(private httpClient: HttpClient) {
  }

  getLotsForIC(): Observable<CarLotInfoForIC[]> {
    // TODO: change to the correct icName
    const icName = 'Insurance Company 1';
    const params = new HttpParams().append('icName', icName);
    return this.httpClient.get<CarLotInfoForIC[]>(
      'http://localhost:8080/car-lot/lots-for-ic', {params});
  }

  deleteLot(lotId: number): Observable<any> {
    const params = new HttpParams().append('lotId', lotId?.toString());
    return this.httpClient.delete<any>('http://localhost:8080/car-lot', {params});
  }
}
