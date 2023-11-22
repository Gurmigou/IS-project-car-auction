import {Component, Input} from '@angular/core';
import {InsuranceCompanyStats} from "../../../../assets/model/stats";

@Component({
  selector: 'stats-card',
  templateUrl: './stats-card.component.html',
  styleUrls: ['./stats-card.component.css']
})
export class StatsCardComponent {
  @Input()
  statsData: InsuranceCompanyStats | undefined;

  constructor() {
  }
}
