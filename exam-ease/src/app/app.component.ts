import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'exam-ease';
  userRole = false;

  constructor(private router: Router){}
  ngOnInit(): void {
    this.isNavbarShowing();
    if (localStorage.getItem('role')) {
      this.userRole = true;
    }
  }
  isNavbarShowing(){
    const role = localStorage.getItem('role');
    return role != null;
  }
}
