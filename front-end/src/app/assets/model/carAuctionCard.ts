export type CarAuctionInfo = {
  carMake: string;
  carModel: string;
  vin: string;
  damageDescription: string;
  carState: string;
  initialPrice: number;
  currentPrice: number;
  auctionStartDate: string;
  timeLeft: string;
  auctionDuration: string;
  insuranceCompany: string;
}


export type CarAuctionCard = {
  imageUrl: string;
  carMake: string;
  carModel: string;
  damageDescription: string;
  currentPrice: number;
  timeLeft: string;
  insuranceCompany: string;
}

export type CarLotForm = {
  id: number | undefined;
  carMake: string;
  carModel: string;
  vin: string;
  damageDescription: string;
  carState: string;
  initialPrice: number;
  auctionDurationHours: number;
  auctionStart: string;
  withoutPublish: boolean;
  insuranceCompanyId: number;
}
