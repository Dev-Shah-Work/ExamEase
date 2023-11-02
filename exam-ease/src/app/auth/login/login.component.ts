import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { noSpaceAllowed } from 'src/app/Validators/NoSpaceAllowed.validator';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  ngOnInit() {
    this.reactiveForm = new FormGroup({
      email: new FormControl(null,[Validators.required,Validators.email]),
      password: new FormControl(null,[Validators.required,Validators.minLength(7),noSpaceAllowed]),
    });
  }
  reactiveForm: FormGroup;
  onSubmit(){
    console.log(this.reactiveForm.value)
  }

}
