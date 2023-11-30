import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { UserHomePageComponent } from './pages/user-home-page/user-home-page.component';
import { AdminHomePageComponent } from './pages/admin-home-page/admin-home-page.component';
import { AddQuizComponent } from './pages/add-quiz/add-quiz.component';
import { AttemptQuizComponent } from './pages/attempt-quiz/attempt-quiz.component';
import { PageGuard } from './route-guard/page.guard';
import { AuthGuard } from './route-guard/auth.guard';
import { ProfileDetailsComponent } from './pages/profile-details/profile-details.component';
import { ShowQuizComponent } from './pages/show-quiz/show-quiz.component';

const routes: Routes = [
  { path: '', component: LandingPageComponent, canActivate: [AuthGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [AuthGuard] },
  {
    path: 'user-home',
    component: UserHomePageComponent,
    canActivate: [PageGuard],
  },
  {
    path: 'admin-home',
    component: AdminHomePageComponent,
    canActivate: [PageGuard],
  },

  { path: 'add-quiz', component: AddQuizComponent, canActivate: [PageGuard] },
  {
    path: 'attempt-quiz',
    component: AttemptQuizComponent,
    canActivate: [PageGuard],
  },
  {
    path: 'profile-details',
    component: ProfileDetailsComponent,
    canActivate: [PageGuard],
  },
  {
    path: 'show-quiz/:id',
    component: ShowQuizComponent,
    canActivate: [PageGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
