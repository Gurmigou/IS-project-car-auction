import {Component, OnInit} from '@angular/core';
import {InsuranceCompanyListService} from "./insurance-company-list.service";
import {InsuranceCompanyStats} from "../../../assets/model/stats";

@Component({
  selector: 'app-insurance-company-list',
  templateUrl: './insurance-company-list.component.html',
  styleUrls: ['./insurance-company-list.component.css']
})
export class InsuranceCompanyListComponent implements OnInit {
  insuranceStats: InsuranceCompanyStats[] = [];

  constructor(private readonly insuranceCompanyListService: InsuranceCompanyListService) {
  }

  ngOnInit(): void {
    this.insuranceCompanyListService.getStats().subscribe(
      (insuranceStats: InsuranceCompanyStats[]) => {
        this.insuranceStats = insuranceStats;
      }
    );
  }
}
