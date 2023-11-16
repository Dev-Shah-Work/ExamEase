import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root', // providedIn: 'root' ensures that the guard is a singleton and can be injected across the application.
})
export class PageGuard implements CanActivate {
  constructor(private router: Router, private jwtService: JwtHelperService) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    // Your logic to determine whether the route can be activated goes here.
    // For example, you can check if the user is authenticated or has the necessary permissions.

    // If the route can be activated, return true.
    // If not, you can navigate to another route or return false to prevent navigation.
    if (localStorage.getItem('token')) {
      let token = localStorage.getItem('token');
      if (token === null || token === undefined) {
        this.router.navigate(['']);
        return false;
      } else if (this.jwtService.isTokenExpired(token)) {
        localStorage.removeItem('token')
        localStorage.removeItem('id')
        localStorage.removeItem('role')
        this.router.navigate(['']);
        return false;
      }
      return true;
    } else {
      this.router.navigate(['']);
      return false;
    }
  }
}
