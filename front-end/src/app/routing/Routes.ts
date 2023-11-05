import {Routes} from "@angular/router";
import {CarDamagedAuctionComponent} from "../components/car/car-damaged-auction/car-damaged-auction.component";
import {CarDamageLotFormComponent} from "../components/car/car-damage-lot-form/car-damage-lot-form.component";
import {LoginComponent} from "../components/security/login/login.component";
import {RegistrationComponent} from "../components/security/registration/registration.component";
import {AuctionLotsListComponent} from "../components/auction-lots-list/auction-lots-list.component";
import {NotFoundComponent} from "../components/common/not-found/not-found.component";

export const routes: Routes = [
  {path: '', component: CarDamagedAuctionComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegistrationComponent},
  {path: 'new-lot', component: CarDamageLotFormComponent},
  {path: 'lot-list', component: AuctionLotsListComponent},
  {path: '**', component: NotFoundComponent},
];
