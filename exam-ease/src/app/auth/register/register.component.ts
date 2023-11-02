import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
// import { FormControl } from '@angular/forms';
import { OnInit } from '@angular/core';
import { noSpaceAllowed } from 'src/app/Validators/NoSpaceAllowed.validator';
import AppUser from 'src/app/model';
import { UserService } from 'src/app/service/user.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  constructor(private userService:UserService){}
  ngOnInit() {
    this.reactiveForm = new FormGroup({
      firstname: new FormControl(null, [Validators.required, noSpaceAllowed]),
      lastname: new FormControl(null),
      email: new FormControl(null, [Validators.required, Validators.email]),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(7),
        noSpaceAllowed,
      ]),
      phoneNo: new FormControl(null, [Validators.pattern('[0-9]{10}')]),
      role: new FormControl('student', [Validators.required]),
    });
  }
  reactiveForm: FormGroup;
  userDetails: AppUser;
  onSubmit() {
    this.userDetails = this.reactiveForm.value;
    var email = this.userDetails.email.toLowerCase();
    this.userDetails.email = email;
    console.log(this.userDetails)
    this.userService.addUser(this.userDetails).subscribe({
      next:data=>{
        console.log(data)
      },
      error:(err:HttpErrorResponse)=>{
        console.log(err)
      }
    });
    

  }
}
