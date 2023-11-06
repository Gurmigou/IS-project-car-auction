import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {LoginComponent} from './components/security/login/login.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCardModule, MatCardTitle} from "@angular/material/card";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSelectModule} from "@angular/material/select";
import { RegistrationComponent } from './components/security/registration/registration.component';
import {MatExpansionModule} from "@angular/material/expansion";
import { CarDamageLotFormComponent } from './components/car/car-damage-lot-form/car-damage-lot-form.component';
import {MatIconModule} from "@angular/material/icon";
import {MatListModule} from "@angular/material/list";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {DateAdapter, MatNativeDateModule} from "@angular/material/core";
import { CarLotCardComponent } from './components/auction-lots-list/car-lot-card/car-lot-card.component';
import {NgOptimizedImage} from "@angular/common";
import { CarDamagedAuctionComponent } from './components/car/car-damaged-auction/car-damaged-auction.component';
import { ImgCarouselComponent } from './components/common/img-carousel/img-carousel.component';
import { CarDamagedAuctionInformationComponent } from './components/car/car-damaged-auction/car-damaged-auction-information/car-damaged-auction-information.component';
import { CarDamagedAuctionLotInformationComponent } from './components/car/car-damaged-auction/car-damaged-auction-lot-information/car-damaged-auction-lot-information.component';
import { NavBarComponent } from './components/common/nav-bar/nav-bar.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatSidenavModule} from "@angular/material/sidenav";
import {RouterLink, RouterModule} from "@angular/router";
import { FooterComponent } from './components/common/footer/footer.component';
import {routes} from "./routing/Routes";
import { AuctionLotsListComponent } from './components/auction-lots-list/auction-lots-list.component';
import { NotFoundComponent } from './components/common/not-found/not-found.component';
import {HttpClientModule} from "@angular/common/http";
import { InsuranceCompanyListComponent } from './components/insurance-company/insurance-company-list/insurance-company-list.component';
import {NgxMaterialTimepickerModule} from "ngx-material-timepicker";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    CarDamageLotFormComponent,
    CarLotCardComponent,
    CarDamagedAuctionComponent,
    ImgCarouselComponent,
    CarDamagedAuctionInformationComponent,
    CarDamagedAuctionLotInformationComponent,
    NavBarComponent,
    FooterComponent,
    AuctionLotsListComponent,
    NotFoundComponent,
    InsuranceCompanyListComponent
  ],
  imports: [
    BrowserModule, BrowserAnimationsModule,
    MatCardModule, MatInputModule, MatButtonModule,
    FormsModule, MatFormFieldModule, MatCheckboxModule,
    MatSelectModule, ReactiveFormsModule, MatExpansionModule,
    MatIconModule, MatListModule, MatDatepickerModule,
    MatNativeDateModule, NgOptimizedImage, MatToolbarModule,
    MatSidenavModule, RouterLink, HttpClientModule,
    RouterModule.forRoot(routes), NgxMaterialTimepickerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}