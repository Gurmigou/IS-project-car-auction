import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CarDamageLotFormService} from "./car-damage-lot-form.service";
import {forkJoin} from "rxjs";
import {CarLotForm} from "../../../assets/model/carAuctionCard";

@Component({
  selector: 'app-car-damage-lot-form-creation',
  templateUrl: './car-damage-lot-form.component.html',
  styleUrls: ['./car-damage-lot-form.component.css']
})
export class CarDamageLotFormComponent implements OnInit {
  carForm: FormGroup;
  uploadedImages: string[] = [];
  uploadedFiles: File[] = [];

  carMakeModels: { [key: string]: string[] } = {};
  public carMakes: string[] = [];
  public carModels: string[] | undefined = [];
  public carStates: string[] | undefined = [];

  constructor(private fb: FormBuilder,
              private carDamageLotFormService: CarDamageLotFormService) {
    this.carForm = this.fb.group({
      carMake: ['', Validators.required],
      carModel: ['', Validators.required],
      vin: ['', Validators.required],
      damageDescription: ['', Validators.required],
      carState: ['', Validators.required],
      lotInitialAmount: ['', Validators.required],
      auctionStartDate: ['', Validators.required],
      auctionDuration: ['', Validators.required],
      withoutPublish: [false],
    })
  }

  ngOnInit(): void {
    forkJoin([
      this.carDamageLotFormService.getStaticDate(),
      this.carDamageLotFormService.getStaticDateStates()
    ])
      .subscribe(([carMakeModels, carStates]) => {
          this.carMakeModels = carMakeModels;
          this.carMakes = Array.from(Object.keys(carMakeModels));
          this.carStates = carStates;
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
    const carLot: CarLotForm = {
      id: undefined,
      carMake: this.carForm.get('carMake')?.value,
      carModel: this.carForm.get('carModel')?.value,
      vin: this.carForm.get('vin')?.value,
      damageDescription: this.carForm.get('damageDescription')?.value,
      carState: (this.carForm.get('carState')?.value as string)
        .replaceAll(' ', '_')
        .toUpperCase(),
      initialPrice: this.carForm.get('lotInitialAmount')?.value,
      auctionDurationHours: this.carForm.get('auctionDuration')?.value,
      auctionStart: this.carForm.get('auctionStartDate')?.value,
      withoutPublish: this.carForm.get('withoutPublish')?.value,
      insuranceCompanyId: 1
      // Number(localStorage.getItem('insuranceCompanyId'))
    }
    this.carDamageLotFormService.saveCarLot(carLot, this.uploadedFiles)
      .subscribe(
        (response) => {
          console.log(response);
        }
      );
  }
}
