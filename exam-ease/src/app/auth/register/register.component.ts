import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
// import { FormControl } from '@angular/forms';
import { OnInit } from '@angular/core';
import { noSpaceAllowed } from 'src/app/Validators/NoSpaceAllowed.validator';
import AppUser from 'src/app/model';
import { UserService } from 'src/app/service/user.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Router} from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {

  constructor(private userService:UserService,private router:Router){}
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
      confirmPassword:new FormControl(null,[Validators.required]),
      phoneNo: new FormControl(null, [Validators.pattern('[0-9]{10}')]),
      role: new FormControl('user'),
    });
  }
  reactiveForm: FormGroup;
  userDetails: AppUser;
  onSubmit() {
    let {confirmPassword}=this.reactiveForm.value;
    delete this.reactiveForm.value.confirmPassword;
    console.log(this.reactiveForm.value)
    this.userDetails = this.reactiveForm.value;
    console.log(this.userDetails)
    if(this.userDetails.password==confirmPassword){var email = this.userDetails.email.toLowerCase();
    this.userDetails.email = email;
    console.log(this.userDetails)
    this.userService.addUser(this.userDetails).subscribe({
      next:data=>{
        console.log(data)
        if(data){
          alert("Registration Successful")
          this.router.navigate([''])
        }
      },
      error:(err:HttpErrorResponse)=>{
        window.alert(err.error.message)
      }
      });
    }
    else{
      alert("Password mismatch")
      
      
    }
    

  }
}
