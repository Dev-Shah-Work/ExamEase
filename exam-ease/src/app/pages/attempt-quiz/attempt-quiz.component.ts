import { HttpErrorResponse } from '@angular/common/http';
import {
  Component,
  EventEmitter,
  Inject,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { noSpaceAllowed } from 'src/app/Validators/NoSpaceAllowed.validator';
import { QuizGuidelineComponent } from 'src/app/component/quiz-guideline/quiz-guideline.component';
import { Question, Quiz } from 'src/app/model';
import { QuizService } from 'src/app/service/quiz.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { LocationStrategy } from '@angular/common';

@Component({
  selector: 'app-attempt-quiz',
  templateUrl: './attempt-quiz.component.html',
  styleUrls: ['./attempt-quiz.component.css'],
})
export class AttemptQuizComponent implements OnInit {
  constructor(
    private quizService: QuizService,
    public dialog: MatDialog,
    private LocationSt:LocationStrategy
   
  ) {}
  ngOnInit(): void {
    this.quizService.getSubcategories().subscribe({
      next: (data) => {
        this.subcategories = data;
        console.log(this.subcategories);
      },
      error: (err) => {
        console.log(err);
      },
    });
    this.quizService.getQuizes().subscribe({
      next: (data) => {
        this.quizes = data;
        this.selectedQuizes = this.quizes;
        console.log(this.quizes);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
  subcategories: any;
  quizes: any;
  selectedQuizes: any;
  selectedSubcategoryIndex: number | null = null;
  selectSubcategory(index: number, id: number): void {
    this.selectedSubcategoryIndex =
      this.selectedSubcategoryIndex === index ? null : index;
    this.selectedQuizes = this.quizes.filter(
      (quiz) => quiz.subcategory.id === id
    );
  }
  allSubcategoryClicked() {
    this.selectedSubcategoryIndex = null;
    console.log('In all subcategory clicked');
    this.selectedQuizes = this.quizes;
  }
  openDialog() {
    const dialogRef = this.dialog.open(QuizGuidelineComponent);

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
    });
  }
  
}
