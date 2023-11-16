import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { noSpaceAllowed } from 'src/app/Validators/NoSpaceAllowed.validator';
import AppUser from 'src/app/model';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.css'],
})
export class ProfileDetailsComponent implements OnInit {
  constructor(private userService: UserService, private router: Router) {}
  doUpdate: boolean = false;
  onUpdate() {
    this.doUpdate = true;
  }
  ngOnInit() {
    this.userService.getUser().subscribe({
      next:(data)=>{
        this.userInformation=data;
        console.log(this.userInformation);
        this.reactiveForm = new FormGroup({
          firstname: new FormControl(this.userInformation.firstname, [Validators.required, noSpaceAllowed]),
          lastname: new FormControl(this.userInformation.lastname),
          email: new FormControl(this.userInformation.email, [Validators.required, Validators.email]),
          phoneNo: new FormControl(this.userInformation.phoneNo, [Validators.pattern('[0-9]{10}')]),
         
        });
        
      },error:(err:HttpErrorResponse)=>{
        window.alert(err.error.message)
      }
    })
   
  }
  reactiveForm: FormGroup;
  userInformation:any;
  userDetails: any;
  onUpdateDetails() {
  
    this.userDetails = this.reactiveForm.value;
    console.log(this.userDetails);
 
      var email = this.userDetails.email.toLowerCase();
      this.userDetails.email = email;
      console.log(this.userDetails);
      this.userService.updateUser(this.userDetails).subscribe({
        next: (data) => {
          console.log(data);
          if (data) {
            alert('Update Successful');
            this.doUpdate=false;
          }
        },
        error: (err: HttpErrorResponse) => {
          window.alert(err.error.message);
        },
      });
   
  }

  goBackClicked() {
    this.doUpdate = false;
  }
}
