import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CarDamageLotFormService} from "./car-damage-lot-form.service";
import {forkJoin, tap} from "rxjs";
import {CarLotForm, CarLotInfoForIC} from "../../../assets/model/carAuctionCard";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-car-damage-lot-form-creation',
  templateUrl: './car-damage-lot-form.component.html',
  styleUrls: ['./car-damage-lot-form.component.css']
})
export class CarDamageLotFormComponent implements OnInit {
  carForm: FormGroup;
  uploadedImages: string[] = [];
  uploadedFiles: File[] = [];

  isCarLotUpdate: boolean = false;
  updateCarLotId: number | undefined;

  carMakeModels: { [key: string]: string[] } = {};
  public carMakes: string[] = [];
  public carModels: string[] | undefined = [];
  public carStates: string[] | undefined = [];

  constructor(private fb: FormBuilder,
              private router: Router,
              private route: ActivatedRoute,
              private carDamageLotFormService: CarDamageLotFormService) {
    this.carForm = this.fb.group({
      carMake: ['', Validators.required],
      carModel: ['', Validators.required],
      vin: ['', Validators.required],
      damageDescription: ['', Validators.required],
      carState: ['', Validators.required],
      lotInitialAmount: ['', Validators.required],
      auctionStartDate: ['', Validators.required],
      auctionStartTime: ['', Validators.required],
      auctionDuration: ['', Validators.required],
      withoutPublish: [false],
    })
  }

  ngOnInit(): void {
    console.log('onInit')
    forkJoin([
      this.carDamageLotFormService.getStaticDate(),
      this.carDamageLotFormService.getStaticDateStates()
    ])
      .subscribe(([carMakeModels, carStates]) => {
          this.carMakeModels = carMakeModels;
          this.carMakes = Array.from(Object.keys(carMakeModels));
          this.carStates = carStates;

          // Get the carLotData from localStorage
          if (localStorage.getItem('carLotData')) {
            const carLot: CarLotInfoForIC = JSON.parse(localStorage.getItem('carLotData')!);
            this.carForm.get('carMake')?.setValue(carLot.carMake);
            this.carForm.get('carModel')?.setValue(carLot.carModel);
            this.carForm.get('vin')?.setValue(carLot.vin);
            this.carForm.get('damageDescription')?.setValue(carLot.damageDescription);

            const carState = carLot.carState.toLowerCase().replaceAll('_', ' ');
            const carStateFormatted = carState.charAt(0).toUpperCase() + carState.slice(1);
            console.log('carStateFormatted: ' + carStateFormatted)

            this.carForm.get('carState')?.setValue(carStateFormatted);
            this.carForm.get('withoutPublish')?.setValue(true);
            this.updateCarLotId = carLot.lotId;
            this.isCarLotUpdate = true;
            localStorage.removeItem('carLotData');
          }
        }
      );

    // Subscribe to changes in carMake form control
    this.carForm.get('carMake')!.valueChanges.subscribe(make => {
      this.carModels = this.getModelsForMake(make);
      this.carForm.get('carModel')!.setValue(''); // Reset the carModel selection
    });
  }

  isWithoutPublish() {
    return this.carForm.get('withoutPublish')?.value;
  }

  getModelsForMake(make: string): string[] {
    return this.carMakeModels[make];
  }

  onImageUpload(event: any) {
    for (let file of event.target.files) {
      this.uploadedFiles.push(file);
      let reader = new FileReader();
      reader.onload = (e: any) => {
        this.uploadedImages.push(e.target.result);
      }
      reader.readAsDataURL(file);
    }
  }

  removeImage(index: number) {
    this.uploadedImages.splice(index, 1);
    this.uploadedFiles.splice(index, 1);
  }

  isSubmitFormEnabled(): boolean {
    return this.uploadedImages.length > 0 && (this.carForm.valid || (this.isWithoutPublish() &&
      this.carForm.get('carMake')?.value && this.carForm.get('carModel')?.value &&
      this.carForm.get('vin')?.value && this.carForm.get('damageDescription')?.value &&
      this.carForm.get('carState')?.value));
  }

  onFormSubmit() {
    console.log('auctionStartDate: ' + (this.carForm.get('auctionStartDate')?.value instanceof Date))
    console.log('auctionStartTime: ' + this.carForm.get('auctionStartTime')?.value)

    const carLot: CarLotForm = {
      id: undefined,
      carMake: this.carForm.get('carMake')?.value,
      carModel: this.carForm.get('carModel')?.value,
      vin: this.carForm.get('vin')?.value,
      damageDescription: this.carForm.get('damageDescription')?.value,
      carState: (this.carForm.get('carState')?.value as string)
        .replaceAll(' ', '_')
        .toUpperCase(),
      initialPrice: 0,
      auctionDurationHours: 0,
      auctionStart: '',
      withoutPublish: this.carForm.get('withoutPublish')?.value,
    }
    if (!this.isWithoutPublish()) {
      carLot.initialPrice = this.carForm.get('lotInitialAmount')?.value;
      carLot.auctionDurationHours = this.getAuctionDurationInHours();
      carLot.auctionStart = this.combineDateTime(
        this.carForm.get('auctionStartDate')?.value,
        this.carForm.get('auctionStartTime')?.value);
    }
    if (this.isCarLotUpdate) {
      carLot.id = this.updateCarLotId;
      this.carDamageLotFormService.updateCarLot(carLot, this.uploadedFiles)
        .pipe(
          tap(() => {
            if (this.isWithoutPublish()) {
              this.router.navigate(['/ic/lot-list'], {replaceUrl: true})
            } else {
              this.router.navigate(['/ic/auction-list'], {replaceUrl: true})
            }
          }),
        )
        .subscribe();
    } else {
      this.carDamageLotFormService.saveCarLot(carLot, this.uploadedFiles)
        .pipe(
          tap(() => {
            if (this.isWithoutPublish()) {
              this.router.navigate(['/ic/lot-list'], {replaceUrl: true})
            } else {
              this.router.navigate(['/ic/auction-list'], {replaceUrl: true})
            }
          }),
        )
        .subscribe();
    }
  }

  private getAuctionDurationInHours(): number {
    const durationValue = this.carForm.get('auctionDuration')?.value;
    const [duration, unit] = durationValue.split(' ');
    if (unit === 'hours') {
      return Number(duration);
    } else {
      return Number(duration) * 24;
    }
  }

  // Assuming dateStart is a JavaScript Date object and timeStart is a string in "hh:mm AM/PM" format
  private combineDateTime(dateStart: Date, timeStart: string): string {
    // Create a new Date object from the dateStart to avoid mutating the original date
    let combinedDateTime = new Date(dateStart.getTime());

    // Extract hours and minutes from the timeStart string
    let [time, modifier] = timeStart.split(' ');
    let [hours, minutes] = time.split(':').map(Number);

    // Convert 12-hour time format to 24-hour time format
    if (hours === 12) {
      hours = modifier.toUpperCase() === 'AM' ? 0 : 12;
    } else {
      hours = modifier.toUpperCase() === 'PM' ? hours + 12 : hours;
    }

    console.log('hours: ' + hours)
    console.log('minutes: ' + minutes)

    // Set the hours and minutes to the combinedDateTime object
    combinedDateTime.setHours(hours, minutes);

    // Convert the JavaScript Date object to an ISO 8601 string format
    console.log('combinedDateTime: ' + combinedDateTime.toISOString())
    return combinedDateTime.toISOString();
  }
}
