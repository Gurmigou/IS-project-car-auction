import {Component} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {InterationType} from "./login.model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  formGroup: FormGroup;
  loginType: InterationType = InterationType.USER;

  constructor() {
    this.formGroup = new FormGroup({
      username: new FormControl(''),
      password: new FormControl(''),
    });
  }

  submit() {
    console.log('submit form')
  }
}
