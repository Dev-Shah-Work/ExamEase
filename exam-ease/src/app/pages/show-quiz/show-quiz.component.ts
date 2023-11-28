import { LocationStrategy ,Location} from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-show-quiz',
  templateUrl: './show-quiz.component.html',
  styleUrls: ['./show-quiz.component.css']
})
export class ShowQuizComponent implements OnInit,OnDestroy{
  constructor(private location:Location){}
  
  public locationSubscription:any;
  ngOnInit(): void {
    this.location.subscribe(()=>{
      console.log("User Navigated")
    })
  }
  ngOnDestroy(): void {
    if(this.locationSubscription){
      this.locationSubscription.unsubscribe();
    }
  }
  
}


