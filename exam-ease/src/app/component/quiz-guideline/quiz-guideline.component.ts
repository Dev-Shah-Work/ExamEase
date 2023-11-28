import { Component } from "@angular/core";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
    selector: 'quiz-guideline',
    templateUrl: 'quiz-guideline.component.html',
    standalone: true,
  })
  export class QuizGuidelineComponent {
    constructor(private dialogRef: MatDialogRef<QuizGuidelineComponent>){

    }
  }