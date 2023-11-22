import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {concatMap, map, tap} from "rxjs";
import {CarDamagedAuctionService} from "./car-damaged-auction.service";
import {CarDetailedInfo} from "../../../assets/model/carAuctionCard";

@Component({
  selector: 'app-car-damaged-auction',
  templateUrl: './car-damaged-auction.component.html',
  styleUrls: ['./car-damaged-auction.component.css']
})
export class CarDamagedAuctionComponent implements OnInit {
  lotId: number | undefined;
  carDetailed: CarDetailedInfo | undefined;

  constructor(private route: ActivatedRoute,
              private carDamagedAuctionService: CarDamagedAuctionService) {
  }

  ngOnInit(): void {
    this.route.params.pipe(
      map(params => +params['lotId']),
      tap(lotId => this.lotId = lotId),
      concatMap(lotId => this.carDamagedAuctionService.getLotInfo(lotId))
    ).subscribe(carDetailed => {
        this.carDetailed = carDetailed;
      }
    )
  }
}
