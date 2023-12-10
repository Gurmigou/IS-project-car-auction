import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {concatMap, Observable, of} from "rxjs";
import {CarLotForm} from "../../../assets/model/carAuctionCard";

@Injectable({
  providedIn: 'root'
})
export class CarDamageLotFormService {

  constructor(private httpClient: HttpClient) {
  }

  public getStaticDate(): Observable<{ [key: string]: string[] }> {
    return this.httpClient.get<{ [key: string]: string[] }>('http://localhost:8080/car/static');
  }

  public getStaticDateStates(): Observable<string[]> {
    return this.httpClient.get<string[]>('http://localhost:8080/car/static/states');
  }

  public saveCarLot(carLot: CarLotForm, images: File[]): Observable<any> {
    return this.httpClient.post<number>('http://localhost:8080/car-lot', carLot).pipe(
      concatMap((carLotId: number) => this.uploadImagesForCarLot(carLotId, images))
    );
  }

  public updateCarLot(carLot: CarLotForm, images: File[]): Observable<any> {
    return this.httpClient.put<number>('http://localhost:8080/car-lot', carLot).pipe(
      concatMap((carLotId: number) => this.uploadImagesForCarLot(carLotId, images))
    );

  }

  private uploadImagesForCarLot(carLotId: number, images: File[]): Observable<any> {
    // Write code to get know what type is the image
    images.forEach((image, index) => console.log(`Image ${index}: ${image.name} is of type File ${image instanceof File}`));

    const formData: FormData = new FormData();

    // Add each image to the form data
    images.forEach((image, index) => {
      formData.append(`images`, image, image.name);
    });

    // Include the carLotId in the request
    formData.append('carLotId', carLotId.toString());

    console.log('formData', formData)
    return this.httpClient.post(`http://localhost:8080/car-lot/images`, formData);
  }
}
