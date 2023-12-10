export type CarDetailedInfo = {
  images: string[]; // in Base64 format
  lotInfo: CarLotInfo;
  auctionInfo: CarAuctionInfo;
}

export type CarLotInfo = {
  carMake: string;
  carModel: string;
  vin: string;
  damageDescription: string;
  carState: string;
  insuranceCompany: string;
}

export type CarLotInfoForIC = CarLotInfo & {
  lotId: number;
  image: string
};

export type CarAuctionInfoForIC = {
  auctionId: number;
  lotId: number;
  carMake: string;
  carModel: string;
  vin: string;
  damageDescription: string;
  carState: string;
  image: string,
  initialPrice: number;
  auctionStartDate: string;
  auctionDuration: string;
  timeLeft: string;
  bidsList: [{
    bidAmount: number;
    userName: string;
  }]
}

export type CarAuctionInfo = {
  auctionId: number;
  initialPrice: number;
  auctionStartDate: string;
  auctionDuration: string;
  timeLeft: string;
  currentPrice: number;
}


export type CarAuctionCard = {
  lotId: number;
  image: string;
  carMake: string;
  carModel: string;
  damageDescription: string;
  currentPrice: number;
  timeLeft: string;
  insuranceCompany: string;
}

export type MyBidCard = {
  auctionId: number;
  lotId: number;
  image: string;
  carMake: string;
  carModel: string;
  insuranceCompany: string;
  bidAmounts: number[];
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
