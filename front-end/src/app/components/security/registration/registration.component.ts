import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {InterationType} from "../login/login.model";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  registrationForm: FormGroup;
  registrationType: InterationType = InterationType.USER;

  constructor() {
    this.registrationForm = new FormGroup({
      registrationType: new FormControl('', [Validators.required]),
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      confirmPassword: new FormControl('', [Validators.required])
    });
  }

  submit() {
    if (this.registrationForm.valid) {
      console.log(this.registrationForm.value);
      // Proceed with the registration logic...
    }
  }

  getType() {
    console.log('current type:', this.registrationType);
    return this.registrationType;
  }
}
