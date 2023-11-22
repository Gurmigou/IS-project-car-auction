import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {InsuranceCompanyStats} from "../../../assets/model/stats";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class InsuranceCompanyListService {
  constructor(readonly httpClient: HttpClient) {
  }

  getStats(): Observable<InsuranceCompanyStats[]> {
    return this.httpClient.get<InsuranceCompanyStats[]>('http://localhost:8080/stats/insurance-company/all');
  }
}
