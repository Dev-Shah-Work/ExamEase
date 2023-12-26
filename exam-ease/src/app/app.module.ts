import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  HTTP_INTERCEPTORS,
  HttpClientModule,
  HttpRequest,
} from '@angular/common/http';
import { AppComponent } from './app.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { NavbarComponent } from './component/navbar/navbar.component';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { JWT_OPTIONS, JwtHelperService, JwtModule } from '@auth0/angular-jwt';
import { UserHomePageComponent } from './pages/user-home-page/user-home-page.component';
import { AdminHomePageComponent } from './pages/admin-home-page/admin-home-page.component';
import { AddQuizComponent } from './pages/add-quiz/add-quiz.component';
import { AttemptQuizComponent } from './pages/attempt-quiz/attempt-quiz.component';
import { ProfileDetailsComponent } from './pages/profile-details/profile-details.component';
import { MatCardModule } from '@angular/material/card';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatSidenavModule } from '@angular/material/sidenav';
import { ClickHighlightDirective } from './Directives/click-highlight.directive';
import { MatDialogModule } from '@angular/material/dialog';
import { DropdownModule } from 'primeng/dropdown';

import { ChartModule } from 'primeng/chart';
import { CarouselModule } from 'primeng/carousel';
import { ShowQuizComponent } from './pages/show-quiz/show-quiz.component';
import { CountdownTimerComponent } from './component/countdown-timer/countdown-timer.component';
import { KnobModule } from 'primeng/knob';
import { InputSwitchModule } from 'primeng/inputswitch';
import { CardModule } from 'primeng/card';
import { TableModule } from 'primeng/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { AuthInterceptorService } from './service/auth-interceptor.service';
import { NumericOnlyDirective } from './Directives/numeric-only.directive';

@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent,
    NavbarComponent,
    RegisterComponent,
    LoginComponent,
    UserHomePageComponent,
    AdminHomePageComponent,
    AddQuizComponent,
    AttemptQuizComponent,
    ProfileDetailsComponent,
    ClickHighlightDirective,
    ShowQuizComponent,
    CountdownTimerComponent,
    NumericOnlyDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatRadioModule,
    MatDividerModule,
    MatListModule,
    MatExpansionModule,
    MatPaginatorModule,
    MatSidenavModule,
    MatDialogModule,
    CarouselModule,
    ChartModule,
    CardModule,
    KnobModule,
    HttpClientModule,
    DropdownModule,
    TableModule,
    InputSwitchModule,

    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        allowedDomains: ['example.com'],
        disallowedRoutes: ['http://example.com/examplebadroute/'],
      },
    }),
    BrowserAnimationsModule,
    FormsModule,
  ],
  providers: [
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },

    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true,
    },
    JwtHelperService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
function tokenGetter(request?: HttpRequest<any>): string | Promise<string> {
  return localStorage.getItem('token');
}
