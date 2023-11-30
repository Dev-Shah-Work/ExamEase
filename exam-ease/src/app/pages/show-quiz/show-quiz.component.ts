import { LocationStrategy, Location } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { QuizService } from 'src/app/service/quiz.service';

@Component({
  selector: 'app-show-quiz',
  templateUrl: './show-quiz.component.html',
  styleUrls: ['./show-quiz.component.css'],
})
export class ShowQuizComponent implements OnInit, OnDestroy {
  constructor(
    private location: Location,
    private quizService: QuizService,
    private fb: FormBuilder
  ) {}

  public locationSubscription: any;
  ngOnInit(): void {
    // // For disabling back functionality of Browser
    // this.location.subscribe(()=>{
    //   console.log("User Navigated")
    // })
    this.quizService.getQuizById(25).subscribe({
      next: (data) => {
        this.quiz = data;
        this.questions = this.quiz.questions;
        console.log(this.quiz);
        this.questions.forEach((element) => {
          (this.responseForm.get('responses') as FormArray).push(
            this.buildQuestionForm(element)
          );
        });
      },
      error: (err) => {
        console.log(err);
      },
    });
    this.responseForm = this.fb.group({
      responses: this.fb.array([]),
    });
   
    console.log(this.responseForm)
    // console.log(this.responseForm.get('responses')['controls'])
  }
  ngOnDestroy(): void {
    if (this.locationSubscription) {
      this.locationSubscription.unsubscribe();
    }
  }
  quiz: any;
  questions: any;
  testScore: Number;
  attemptTime: Number = new Date().getTime();
  completionTime: Number;
  responses: Response[] = [];
  currentResponse: Response;
  quizTakerId: Number = parseInt(localStorage.getItem('id'));
  quizId: Number;
  responseForm: FormGroup;

  buildQuestionForm(question) {
    const questionGroup = this.fb.group({
      score: this.fb.control(null),
      responseText: this.fb.control(null),
      responseId: this.fb.group({
        id: this.fb.control(null),
      }),
      question: this.fb.control(question.id),
    });
    return questionGroup;
  }

  //
}
