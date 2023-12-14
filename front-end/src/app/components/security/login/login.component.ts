import {Component} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {InterationType} from "./login.model";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  formGroup: FormGroup;
  loginType: InterationType = InterationType.USER;

  constructor(private httpClient: HttpClient, private router: Router) {
    this.formGroup = new FormGroup({
      email: new FormControl(''),
      password: new FormControl(''),
      insuranceCompanyName: new FormControl(''),
    });
  }

  submit() {
    const loginData = {
      ...this.formGroup.value,
    };
    this.httpClient.post<any>('http://localhost:8080/security/login', loginData)
      .subscribe((token) => {
        console.log(token)
        if (this.loginType === InterationType.USER) {
          localStorage.setItem('type', 'user');
          this.router.navigate(['/auction-list'], {replaceUrl: true});
        } else {
          localStorage.setItem('type', 'ic');
          this.router.navigate(['/new-lot'], {replaceUrl: true});
        }
        localStorage.setItem('token', token.jwtToken);
      });
  }

  gotoRegister() {
    this.router.navigate(['/register'], {replaceUrl: true});
  }
}
