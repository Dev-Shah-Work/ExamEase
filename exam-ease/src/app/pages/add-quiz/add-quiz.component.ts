import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { QuizService } from 'src/app/service/quiz.service';

@Component({
  selector: 'app-add-quiz',
  templateUrl: './add-quiz.component.html',
  styleUrls: ['./add-quiz.component.css']
})
export class AddQuizComponent implements OnInit{

  constructor(private quizService:QuizService){}
  ngOnInit(): void {
    this.quizService.getCategories().subscribe({
      next:(data)=>{
        this.categories=data
        console.log(this.categories)
      },
      error:(err:HttpErrorResponse)=>{
        console.log(err)
      }
    })
  }
  categories:any;
  subcategories:any
 
  onCategoryChange(val:number) {
      this.subcategories=this.categories[val].subcategories;
    }

}
