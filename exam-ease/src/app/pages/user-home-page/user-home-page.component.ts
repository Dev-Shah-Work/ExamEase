import {
  ChangeDetectionStrategy,
  Component,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import { QuizService } from 'src/app/service/quiz.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-home-page',
  templateUrl: './user-home-page.component.html',
  styleUrls: ['./user-home-page.component.css'],
})
export class UserHomePageComponent implements OnInit {
  constructor(
    private userService: UserService,
    private quizService: QuizService
  ) {
    this.items = [];
    this.itemsQuiz = [];
  }

  ngOnInit() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue(
      '--text-color-secondary'
    );
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
    this.userService.getLeaderBoardStats().subscribe({
      next: (data) => {
        this.leaderboardStats = data;
        console.log(this.leaderboardStats);
      },
      error: (err) => {
        console.log(err);
      },
    });
    this.quizService.getPortalStats().subscribe({
      next: (response) => {
        this.portalStats = response;
        console.log(this.portalStats);
      },
      error: (err) => [console.log(err)],
    });
    this.userService.getTestAnalytics().subscribe({
      next: (response) => {
        this.testAnalytics = response;
       
        for (const currentTest of this.testAnalytics) {
          this.items.push({
            label: currentTest.testIdentifier.slice(
              0,
              currentTest.testIdentifier.length - 7
            ),
            value: currentTest.id,
          });
        }
        this.currentTestId = this.items.length > 0 ? this.items[0].value : null;
      },
      error: (err) => {
        console.log(err);
      },
    });
    this.userService.getQuizAnalytics().subscribe({
      next: (response) => {
        this.quizAnalytics = response;
        if (this.quizAnalytics.length === 0) {
          this.isQuizEmpty = true;
        }
        console.log(this.quizAnalytics);

        for (const currentQuiz of this.quizAnalytics) {
          this.itemsQuiz.push({
            label: currentQuiz.quizName,
            value: currentQuiz.id,
          });
        }
      },
      error: (err) => {
        console.log(err);
      },
    });
    this.doughnutOptions = {
      cutout: '60%',
      plugins: {
        legend: {
          labels: {
            color: 'black',
          },
        },
      },
    };
    this.barOptions = {
      mainAspectRatio: false,
      plugins: {
        legend: {
          labels: {
            color: textColor,
          },
        },
      },
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false,
          },
        },
        x: {
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false,
          },
        },
      },
      aspectRatio: true,
    };
    this.horizontalbarOptions = {
      indexAxis: 'y',
      maintainAspectRatio: false,
      aspectRatio: 0.8,
      plugins: {
        legend: {
          labels: {
            color: textColor,
          },
        },
      },
      scales: {
        x: {
          ticks: {
            color: textColorSecondary,
            font: {
              weight: 500,
            },
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false,
          },
        },
        y: {
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false,
          },
        },
      },
    };
  }
  userTd = localStorage.getItem('id');
  leaderboardStats: any;
  testAnalytics: any;
  quizAnalytics: any;
  dataBar: any;
  dataHorizontalBar: any;
  dataQuizBarScore: any;
  dataQuizBarDuration: any;
  showTest;
  doughnutOptions: any;
  barOptions: any;
  horizontalbarOptions: any;
  portalStats: any;
  dataDoughnut: any;
  currentTestId: any;
  currentTest: any;
  currentQuizId: any;
  currentQuiz: any;
  totalAttempts: any;
  showTestAnalytics = true;
  showQuizAnalytics = false;
  items: any = [];
  itemsQuiz: any = [];
  isQuizEmpty = false;
  
  runThis() {
    console.log(this.showTest);
  }
  dropdownClicked() {
    this.currentTest = this.testAnalytics.filter(
      (data) => data.id === this.currentTestId
    );
    this.showTestAnalytics = true;
    console.log(this.currentTestId);
    console.log(this.currentTest);
    const documentStyle = getComputedStyle(document.documentElement);

    this.dataDoughnut = {
      labels: ['Right Questions', 'Wrong Questions', 'Unattempted Questions'],

      datasets: [
        {
          data: [
            this.currentTest[0].rightQuestions,
            this.currentTest[0].wrongQuestions,
            this.currentTest[0].unattemptedQuestions,
          ],
          backgroundColor: [
            documentStyle.getPropertyValue('--blue-500'),
            documentStyle.getPropertyValue('--yellow-500'),
            documentStyle.getPropertyValue('--green-500'),
          ],
          hoverBackgroundColor: [
            documentStyle.getPropertyValue('--blue-400'),
            documentStyle.getPropertyValue('--yellow-400'),
            documentStyle.getPropertyValue('--green-400'),
          ],
        },
      ],
    };
    this.dataBar = {
      labels: ['Your Score', 'Avg Score', 'Max Score'],
      datasets: [
        {
          label: 'Scores',
          data: [
            this.currentTest[0].score,
            this.currentTest[0].avgScore,
            this.currentTest[0].maxScore,
          ],
          backgroundColor: [
            'rgba(255, 159, 64, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(54, 162, 235, 0.2)',
          ],
          borderColor: [
            'rgb(255, 159, 64)',
            'rgb(75, 192, 192)',
            'rgb(54, 162, 235)',
          ],
          borderWidth: 1,
        },
      ],
    };
    this.dataHorizontalBar = {
      labels: ['Duration'],
      datasets: [
        {
          label: 'Your Duration',
          backgroundColor: documentStyle.getPropertyValue('--blue-500'),
          borderColor: documentStyle.getPropertyValue('--blue-500'),
          data: [this.currentTest[0].duration / 60],
        },
        {
          label: 'Total Duration alloted',
          backgroundColor: documentStyle.getPropertyValue('--pink-500'),
          borderColor: documentStyle.getPropertyValue('--pink-500'),
          data: [this.currentTest[0].maxDuration / 60],
        },
      ],
    };
  }
  dropdownQuizClicked() {
    this.currentQuiz = this.quizAnalytics.filter(
      (data) => data.id === this.currentQuizId
    );
    this.totalAttempts = this.currentQuiz[0].attemptCount;
    this.showQuizAnalytics = true;
    console.log(this.currentQuiz);
    console.log(this.currentQuizId);

    const documentStyle = getComputedStyle(document.documentElement);
    this.dataQuizBarScore = {
      labels: ['Min Score', 'Avg Score', 'Max Score'],
      datasets: [
        {
          label: 'Scores',
          data: [
            this.currentQuiz[0].score,
            this.currentQuiz[0].avgScore,
            this.currentQuiz[0].maxScore,
          ],
          backgroundColor: [
            'rgba(255, 159, 64, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(54, 162, 235, 0.2)',
          ],
          borderColor: [
            'rgb(255, 159, 64)',
            'rgb(75, 192, 192)',
            'rgb(54, 162, 235)',
          ],
          borderWidth: 1,
        },
      ],
    };
    this.dataQuizBarDuration = {
      labels: ['Average Duration', 'Total Duration'],
      datasets: [
        {
          label: 'Time Consumed',
          data: [
            this.currentQuiz[0].avgDuration / 60,
            this.currentQuiz[0].duration / 60,
          ],
          backgroundColor: [
            'rgba(255, 159, 64, 0.2)',
            'rgba(75, 192, 192, 0.2)',
          ],
          borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)'],
          borderWidth: 1,
        },
      ],
    };
  }
  // generateChartData(testAnalytic: any): any {
  //   console.log('start');

  //   const documentStyle = getComputedStyle(document.documentElement);
  //   const currentTest = this.testAnalytics.filter(
  //     (data) => data.id === testAnalytic
  //   );
  //   // Dynamic generation of chart data based on a single testAnalytic object
  //   const data = {
  //     labels: ['Right Questions', 'Wrong Questions', 'Unattempted Questions'],
  //     datasets: [
  //       {
  //         data: [
  //           currentTest.rightQuestions,
  //           currentTest.wrongQuestions,
  //           currentTest.unattemptedQuestions,
  //         ],
  //         backgroundColor: [
  //           documentStyle.getPropertyValue('--green-500'),
  //           documentStyle.getPropertyValue('--red-500'),
  //           documentStyle.getPropertyValue('--yellow-500'),
  //         ],
  //         hoverBackgroundColor: [
  //           documentStyle.getPropertyValue('--green-400'),
  //           documentStyle.getPropertyValue('--red-400'),
  //           documentStyle.getPropertyValue('--yellow-400'),
  //         ],
  //       },
  //     ],
  //   };
  //   console.log('resukt');

  //   return data;
  // }
}
