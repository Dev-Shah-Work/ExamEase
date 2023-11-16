import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { noSpaceAllowed } from '../Validators/NoSpaceAllowed.validator';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css'],
})
export class LandingPageComponent {
  constructor(private userService: UserService, private route: Router) {}
  loginFlag: boolean = false;
  reactiveForm: FormGroup;
  ngOnInit() {
    this.reactiveForm = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.email]),
      password: new FormControl(null, [Validators.required]),
    });
  }
  onSubmit() {
    console.log(this.reactiveForm.value);
    var data = this.reactiveForm.value;
    var email = data.email.toLowerCase();
    data.email = email;
    this.userService.authenticateUser(data).subscribe({
      next: (data: any) => {
        localStorage.setItem('token', data.token);
        localStorage.setItem('role', data.role);
        localStorage.setItem("id",data.id)
        if (localStorage.getItem('role') === 'user') {
          this.route.navigate(['/user-home']);
        }
        if (localStorage.getItem('role') === 'admin') {
          this.route.navigate(['/admin-home']);
        }
      },
      error: (err: HttpErrorResponse) => {
        console.log(err.message);
      },
    });
  }
  getStartedClicked() {
    if (localStorage.getItem('role') === 'user') {
      this.route.navigate(['/user-home']);
    } else if (localStorage.getItem('role') === 'admin') {
      this.route.navigate(['/admin-home']);
    } else if (!this.loginFlag) {
      this.loginFlag = true;
    }
  }
  goBackClicked() {
    if (this.loginFlag) {
      this.loginFlag = false;
    }
  }
}
