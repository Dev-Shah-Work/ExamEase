import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { noSpaceAllowed } from 'src/app/Validators/NoSpaceAllowed.validator';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private userService:UserService,private route:Router){}
  ngOnInit() {
    this.reactiveForm = new FormGroup({
      email: new FormControl(null,[Validators.required,Validators.email]),
      password: new FormControl(null,[Validators.required,Validators.minLength(7),noSpaceAllowed]),
    });
  }
  reactiveForm: FormGroup;
  onSubmit(){
    console.log(this.reactiveForm.value);
    var data = this.reactiveForm.value;
    var email = data.email.toLowerCase();
    data.email = email;
    this.userService.authenticateUser(data).subscribe({next:(data:any)=>{
      localStorage.setItem("token",data.token)
      localStorage.setItem("role",data.role)
      localStorage.setItem("id",data.id)
      if (localStorage.getItem('role') === 'user') {
        this.route.navigate(['/user-home']);
      }
      if (localStorage.getItem('role') === 'admin') {
        this.route.navigate(['/admin-home']);
      }
    },
    error:(err:HttpErrorResponse)=>{
      console.log(err.message)
    }
  })

  }

}
