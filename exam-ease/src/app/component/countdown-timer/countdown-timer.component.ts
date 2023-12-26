import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-countdown-timer',
  templateUrl: './countdown-timer.component.html',
  styleUrls: ['./countdown-timer.component.css'],
})
export class CountdownTimerComponent implements OnInit {
  ngOnInit(): void {
    this.startTimer();
  }
  
  
  TIME_LIMIT = 20;


  // Initially, no time has passed, but this will count up
  // and subtract from the TIME_LIMIT
  timePassed = 0;
  timeLeft = this.TIME_LIMIT;
  timerInterval = null;
  circleDasharray;
  FULL_DASH_ARRAY=2*3.14*45;  

  formatTimeLeft(time) {
    // The largest round integer less than or equal to the result of time divided being by 60.
    const minutes = Math.floor(time / 60);

    // Seconds are the remainder of the time divided by 60 (modulus operator)
    let seconds = time % 60;

    // If the value of seconds is less than 10, then display seconds with a leading zero
    if (seconds < 10) {
      seconds = parseInt(`0${seconds}`);
    }

    // The output in MM:SS format
    return `${minutes}:${seconds}`;
  }
  startTimer() {
    this.timerInterval = setInterval(() => {
      // The amount of time passed increments by one
      this.timePassed = this.timePassed += 1;
      this.timeLeft = this.TIME_LIMIT - this.timePassed;

      // The time left label is updated
    }, 1000);
    this.setCircleDasharray();
  }
  calculateTimeFraction() {
    const rawTimeFraction = this.timeLeft / this.TIME_LIMIT;
  return rawTimeFraction - (1 / this.TIME_LIMIT) * (1 - rawTimeFraction);
  }
  setCircleDasharray() {
    this.circleDasharray = `${(
      this.calculateTimeFraction() * this.FULL_DASH_ARRAY
    ).toFixed(0)} 283`;
    document
    .getElementById("base-timer-path-remaining")
    .setAttribute("stroke-dasharray", this.circleDasharray);
  
  }
}
