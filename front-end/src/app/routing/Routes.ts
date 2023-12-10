import {Routes} from "@angular/router";
import {CarDamagedAuctionComponent} from "../components/car/car-damaged-auction/car-damaged-auction.component";
import {CarDamageLotFormComponent} from "../components/car/car-damage-lot-form/car-damage-lot-form.component";
import {LoginComponent} from "../components/security/login/login.component";
import {RegistrationComponent} from "../components/security/registration/registration.component";
import {AuctionLotsListComponent} from "../components/auction-lots-list/auction-lots-list.component";
import {NotFoundComponent} from "../components/common/not-found/not-found.component";
import {InsuranceCompanyListComponent} from "../components/insurance-company/insurance-company-list/insurance-company-list.component";
import {MyBidListComponent} from "../components/bids/my-bid-list.component";
import {IcLotListService} from "../components/ic/lots/ic-lot-list.service";
import {IcLotListComponent} from "../components/ic/lots/ic-lot-list.component";

export const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegistrationComponent},
  {path: 'new-lot', component: CarDamageLotFormComponent}, // ic
  {path: 'auction-list', component: AuctionLotsListComponent}, // user
  {path: 'my-bids', component: MyBidListComponent}, // user
  {path: 'approved-auctions', component: NotFoundComponent}, // user: ic approved auction won for user
  {path: 'ic/lot-list', component: IcLotListComponent}, // ic
  {path: 'ic/auction-list', component: NotFoundComponent}, // ic
  {path: 'car-lot/:lotId', component: CarDamagedAuctionComponent}, // both
  {path: 'insurance-company-list', component: InsuranceCompanyListComponent}, // user
  {path: '**', component: NotFoundComponent},
];
