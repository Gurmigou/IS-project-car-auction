import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {InterationType} from "../login/login.model";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  registrationForm: FormGroup;
  registrationType: InterationType = InterationType.USER;

  constructor(private httpClient: HttpClient,
              private router: Router) {
    this.registrationForm = new FormGroup({
      registrationType: new FormControl(''),
      insuranceCompanyName: new FormControl(''),
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
      const data = {
        ...this.registrationForm.value,
      }
      this.httpClient.post<any>('http://localhost:8080/security/register', data)
        .subscribe(() => {
          this.router.navigate(['/login'], {replaceUrl: true});
        });
    }
  }

  getType() {
    return this.registrationType;
  }
}
