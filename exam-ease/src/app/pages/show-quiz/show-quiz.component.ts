import { LocationStrategy, Location } from '@angular/common';
import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Timestamp } from 'rxjs';
import { Test } from 'src/app/model';
import { FullScreenService } from 'src/app/service/fullscreen.service';
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
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private router:Router,

  ) {}

  public locationSubscription: any;
  
  ngOnInit(): void {

    this.fullscreen();
    this.quizId = +this.activatedRoute.snapshot.paramMap.get('id');
    this.quizService.getQuizById(this.quizId).subscribe({
      next: (data) => {
        this.quiz = data;
        this.questions = this.quiz.questions;
        console.log(this.quiz);
        this.questions.forEach((element) => {
          (this.responseForm.get('responses') as FormArray).push(
            this.buildQuestionForm(element)
          );
        });
        this.TIME_LIMIT = this.quiz.duration*60 ;
        this.timeLeft = this.TIME_LIMIT;

        this.startTimer();
        setTimeout(()=>{
          this.onSubmit();
        },this.quiz.duration*60*1000)
        
      },
      error: (err) => {
        console.log(err);
      },
    });
    this.responseForm = this.fb.group({
      responses: this.fb.array([]),
    });

    console.log(this.responseForm.get('responses')['controls']);
  }
  ngOnDestroy(): void {
  
    this.closefullscreen();
    
  }
  panelOpenState;
  quiz: any;
  questions: any;
  testScore: Number;
  attemptTime: Number = new Date().getTime();
  completionTime: Number;
  responses: Response[] = [];
  testObj: Test;

  quizTakerId: Number = parseInt(localStorage.getItem('id'));
  quizId: Number;
  responseForm: FormGroup;
  elem=document.documentElement

  TIME_LIMIT;
  timePassed = 0;
  timeLeft;
  timerInterval = null;
  dummyNumber:number=30;
  @HostListener('document:keydown', ['$event'])
  onKeydownHandler(event: KeyboardEvent) {
    if(!this.isInputElement(event.target)){

      event.preventDefault();
    }
    if(this.isInputElement&&(event.key===('ControlLeft'||'ControlLeft'||'Escape'||'AltLeft'||'AltRight'))){
      event.preventDefault();
    }
    console.log(event)
 }
isInputElement(target: EventTarget | null): boolean {
  return target  instanceof HTMLTextAreaElement;
}
@HostListener('window:keydown',['$event'])
escapeDownHandler(event:KeyboardEvent){
  if(event.key==='Escape'){
    window.alert('Are you sure you want to exit')
  }
}

  buildQuestionForm(question) {
    const questionGroup = this.fb.group({
      score: this.fb.control(null),
      responseText: this.fb.control(null),
      responseId: this.fb.group({
        id: this.fb.control(null),
      }),
      questionId: this.fb.control(question.id),
    });
    return questionGroup;
  }
  onSubmit() {
    // console.log(this.responseForm);
    this.responses = this.responseForm.value.responses;
    console.log(this.responses);
    this.testScore = this.responses.reduce((accumulator, currVal) => {
      return accumulator + currVal['score'];
    }, 0);
    console.log(this.testScore);
    this.completionTime = new Date().getTime();

    this.testObj = {
      testScore: this.testScore,
      attemptTime: this.attemptTime,
      completionTime: this.completionTime,
      quizTakerId: this.quizTakerId,
      quizId: this.quizId,
      responses: this.responses as Response[],
    };
    console.log(this.testObj);
    console.log(new Date(this.testObj.attemptTime as number));
    console.log(new Date(this.testObj.completionTime as number));
    this.quizService.addTest(this.testObj).subscribe({
      next: (data) => {
        console.log(data);
        this.router.navigate(['attempt-quiz'])
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
  updateScore(index, scoreControl: AbstractControl) {
    if (this.questions[index].mcq) {
      if (
        scoreControl.get('responseId.id').value ==
        this.questions[index].answer.id
      ) {
        scoreControl.get('score').setValue(this.questions[index].point);
      } else {
        scoreControl.get('score').setValue(0);
      }
    } else {
      scoreControl.get('score').setValue(this.questions[index].point);
    }
  }
  
 
  formatTimeLeft(time) {
    // The largest round integer less than or equal to the result of time divided being by 60.
    const minutes = Math.floor(time / 60);
  
    // Seconds are the remainder of the time divided by 60 (modulus operator)
    let seconds = time % 60;
  
    // If the value of seconds is less than 10, then add a leading zero
    const formattedSeconds = seconds < 10 ? `0${seconds}` : `${seconds}`; 
  
    // The output in MM:SS format
    return `${minutes}:${formattedSeconds}`;
  }
  
  startTimer() {
    this.timerInterval = setInterval(() => {
      // The amount of time passed increments by one
      this.timePassed = this.timePassed += 1;
      this.timeLeft = this.TIME_LIMIT - this.timePassed;

      // The time left label is updated
    }, 1000);
  }
  fullscreen(){
    if(this.elem.requestFullscreen) this.elem.requestFullscreen();
  }
  closefullscreen(){
    if(document.exitFullscreen){
      document.exitFullscreen();
    }
  }
}
