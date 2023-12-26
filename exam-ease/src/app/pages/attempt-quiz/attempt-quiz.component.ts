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
import { PageEvent } from '@angular/material/paginator';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-attempt-quiz',
  templateUrl: './attempt-quiz.component.html',
  styleUrls: ['./attempt-quiz.component.css'],
})
export class AttemptQuizComponent implements OnInit {
  constructor(
    private quizService: QuizService,
    public dialog: MatDialog,
    private LocationSt: LocationStrategy,
    private userService: UserService
  ) {}
  ngOnInit(): void {
    this.quizService.getPortalStats().subscribe({
      next: (response) => {
        this.totalQuizzesCount= response['totalQuizes'];
        this.selectedQuizzesCount=this.totalQuizzesCount;
      },
      error: (err) => [console.log(err)],
    });
    this.quizService.getSubcategories().subscribe({
      next: (data) => {
        this.subcategories = data;
        console.log(this.subcategories);
      },
      error: (err) => {
        console.log(err);
      },
    });
    this.quizService.getQuizesByPagination(6, 0, null).subscribe({
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
  totalQuizzesCount;
  selectedQuizzesCount;
  currentPageSize = 6;
  currentPageIndex = 0;
  selectedQuizes: any;
  selectedSubcategoryIndex: number | null = null;
  subcategoryId: number | null = null;
  selectSubcategory(index: number, id: number): void {
    this.selectedSubcategoryIndex =
      this.selectedSubcategoryIndex === index ? null : index;
    this.subcategoryId = id;
    this.currentPageIndex=0
    this.quizService.getQuizCountBySubcategoryId(this.subcategoryId).subscribe({
      next:(data)=>{
        this.selectedQuizzesCount=data;
      },
      error:(err)=>{
        console.log(err)
      }
    })

    this.quizService
      .getQuizesByPagination(
        this.currentPageSize,
        this.currentPageIndex,
        this.subcategoryId
      )
      .subscribe({
        next: (data) => {
          this.selectedQuizes = data;
          console.log(this.selectedQuizes);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }
  allSubcategoryClicked() {
    this.selectedSubcategoryIndex = null;
    this.subcategoryId=null
    this.selectedQuizzesCount=this.totalQuizzesCount
    this.quizService.getQuizesByPagination(this.currentPageSize, 0, null).subscribe({
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
  openDialog() {
    const dialogRef = this.dialog.open(QuizGuidelineComponent);

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
    });
  }
  onPageChange(event: PageEvent) {
    this.currentPageIndex = event.pageIndex;
    this.currentPageSize = event.pageSize;
    this.quizService
      .getQuizesByPagination(
        event.pageSize,
        event.pageIndex,
        this.subcategoryId
      )
      .subscribe({
        next: (data) => {
          this.quizes = data;
          this.selectedQuizes = this.quizes;
          console.log(this.quizes);
        },
        error: (err) => {
          console.log(err);
        },
      });
    console.log(event);
    
    console.log(event);
  }
}
