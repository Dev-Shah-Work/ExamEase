import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { noSpaceAllowed } from 'src/app/Validators/NoSpaceAllowed.validator';
import { Question, Quiz } from 'src/app/model';
import { QuizService } from 'src/app/service/quiz.service';

@Component({
  selector: 'app-add-quiz',
  templateUrl: './add-quiz.component.html',
  styleUrls: ['./add-quiz.component.css'],
})
export class AddQuizComponent implements OnInit {
  constructor(private quizService: QuizService) {}
  ngOnInit(): void {
    this.quizService.getCategories().subscribe({
      next: (data) => {
        this.categories = data;
        // console.log(this.categories);
      },
      error: (err: HttpErrorResponse) => {
        console.log(err);
      },
    });
    this.reactiveForm = new FormGroup({
      difficulty: new FormControl(null, Validators.required),
      duration: new FormControl(null,[ Validators.required,Validators.min(10),Validators.max(180)]),
      subcategory: new FormControl(null, Validators.required),
      quizName:new FormControl(null,Validators.required),
      question: new FormGroup(
        {
          point: new FormControl(null, Validators.required),
          questionText: new FormControl(null, Validators.required),

          options: new FormArray([
            new FormGroup({
              optionText: new FormControl(null, [this.noEmptySpaceAllowed,Validators.required]),
            }),
          ]),
          answer: new FormControl(null),
          answerText: new FormControl(null),
          isMcq: new FormControl(null, Validators.required),
        },
        customQuestionValidator
      ),
    });
  }
  categories: any;
  subcategories: any;
  subcategory:String;
  quizName:String
  reactiveForm: FormGroup;
  onQuestionPage: boolean = false;
  isMcq: boolean = null;
  questions: Question[] = [];
  selectAnswer: boolean = false;
  question: Question = {
    point: null,
    questionText: null,
    img: null,
    options: null,
    responses: null,
    answer: null,
    answerText: null,
    isMcq: null,
  };
  quiz: Quiz = {
    user: {
      id: parseInt(localStorage.getItem('id')),
    },
    difficulty: null,
    duration: null,
    subcategory: null,
    quizName:null,
    questions: [],
    tests: null,
  };
  showPreview=false

  onCategoryChange(val: number) {
    this.subcategories = this.categories[val].subcategories;
  }
  addQuestion() {
    console.log('Add question clicked');
    let optionFormArray = this.reactiveForm.get(
      'question.options'
    ) as FormArray;

    this.question.point = this.reactiveForm.value.question.point;
    this.question.isMcq = this.reactiveForm.get('question.isMcq').value;

    this.question.img = null;
    this.question.answerText = this.reactiveForm.get(
      'question.answerText'
    ).value;
    this.question.questionText = this.reactiveForm.get(
      'question.questionText'
    ).value;
    this.question.responses = null;

    if (this.reactiveForm.get('question.isMcq').value) {
      this.question.answer = {
        optionText: this.reactiveForm.get('question.answer').value,
      };
      this.question.options = this.reactiveForm.value.question.options;
    } else {
      this.question.options = null;
    }
    this.questions.push(this.question);
    console.log(this.questions);
    alert('Question Added Successfully');
    this.question = {
      point: null,
      questionText: null,
      img: null,
      options: null,
      responses: null,
      answer: null,
      answerText: null,
      isMcq: null,
    };
    this.isMcq = null;
    optionFormArray.clear();
    // optionFormArray.push(new FormArray([
    //   new FormGroup({
    //     optionText: new FormControl(null, Validators.required),
    //   }),
    // ])),
    this.reactiveForm.patchValue({
      question: {
        point: null,
        questionText: null,
        img: null,

        responses: null,
        answer: null,
        answerText: null,
        isMcq: null,
      },
    });
    this.selectAnswer = false;
    this.reactiveForm.markAsPristine();
    this.reactiveForm.markAsUntouched();
  }
  addOption() {
    let fc = new FormControl(null, [
      Validators.required,
      this.noEmptySpaceAllowed,
    ]);
    fc.setErrors(null);
    (<FormArray>this.reactiveForm.get('question').get('options')).push(
      new FormGroup({
        optionText: fc,
      })
    );
  }
  nextClicked() {
    this.onQuestionPage = true;
    this.quiz.difficulty = this.reactiveForm.get('difficulty').value;
    this.quiz.duration = this.reactiveForm.get('duration').value;
    this.quiz.quizName=this.reactiveForm.get('quizName').value;
    this.quiz.subcategory = {
      id: this.reactiveForm.get('subcategory').value,
    };
    this.showPreview=true
    console.log(this.subcategories)
    this.subcategory=(this.subcategories.find(val=>{return val.id===this.quiz.subcategory.id})).subcategoryText
    console.log(this.reactiveForm.value)
    this.quizName=this.reactiveForm.value['quizName']
  }
  questionType(isMcq: boolean) {
    this.isMcq = isMcq;
  }
  submitAnswer() {
    // this.isMcq = null;
    console.log(this.reactiveForm.get('question'));

    this.selectAnswer = true;
  }
  onSubmit() {
    // console.log(this.reactiveForm.get('question.options.0.optionText').value);
    let optionFormArray = this.reactiveForm.get(
      'question.options'
    ) as FormArray;

    this.question.point = this.reactiveForm.value.question.point;
    this.question.isMcq = this.reactiveForm.get('question.isMcq').value;

    this.question.img = null;
    this.question.answerText = this.reactiveForm.get(
      'question.answerText'
    ).value;
    this.question.questionText = this.reactiveForm.get(
      'question.questionText'
    ).value;
    this.question.responses = null;

    if (this.reactiveForm.get('question.isMcq').value) {
      this.question.answer = {
        optionText: this.reactiveForm.get('question.answer').value,
      };
      this.question.options = this.reactiveForm.value.question.options;
    } else {
      this.question.options = null;
    }
    this.questions.push(this.question);
    console.log(this.questions);
    this.showPreview=false
    alert('Question Added Successfully');
    this.question = {
      point: null,
      questionText: null,
      img: null,
      options: null,
      responses: null,
      answer: null,
      answerText: null,
      isMcq: null,
    };
    this.isMcq = null;
    this.reactiveForm.markAsPristine();
    this.reactiveForm.markAsUntouched();
    this.reactiveForm.reset();
    this.selectAnswer = false;
    this.quiz.questions = this.questions;
    // this.question.options=
    console.log(this.quiz);
    this.onQuestionPage = false;

    this.quizService.addQuiz(this.quiz).subscribe({
      next: (data) => {
        console.log(data);
        this.quiz = {
          user: {
            id: parseInt(localStorage.getItem('id')),
          },
          quizName:null,
          difficulty: null,
          duration: null,
          subcategory: null,
          questions: [],
          tests: null,
        };
      },
      error: (err) => {
        console.log(err);
      },
    });

  }
  getDurationErrorMessage(){
  if(this.reactiveForm.get('duration').hasError('min')){
      return 'Minimum duration should be 10 mins'
    }else if(this.reactiveForm.get('duration').hasError('max')){
      return'Maximum duration should be 180 mins'
    }else{
      return ''
    }
  }
  noEmptySpaceAllowed(control: FormControl): ValidationErrors | null {
    const isOptiontextInvalid =
      control.value == null ||
      control.value == undefined ||
      control.value == ''||
      control.value.trim().length==0
    console.log(isOptiontextInvalid);

    if (isOptiontextInvalid) return { improperQuestion: true };
    return null;
  }
}
const customQuestionValidator = (demoControl: FormGroup) => {
  if (demoControl.get('isMcq').value) {
    if (
      demoControl.get('options').value === null ||
      demoControl.get('answer').value === null
    ) {
      return { improperQuestion: true };
    } else {
      for (let option of demoControl.value.options) {
        if (option.optionText === null) {
          return { improperQuestion: true };
        }
      }
      return null;
    }
  } else {
    if (demoControl.get('answerText').value === null) {
      return { improperQuestion: true };
    } else {
      return null;
    }
  }
};
