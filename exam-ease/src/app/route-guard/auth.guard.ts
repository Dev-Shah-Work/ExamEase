import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root', // providedIn: 'root' ensures that the guard is a singleton and can be injected across the application.
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

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
    if (!localStorage.getItem('token')) {
      return true;
    } else {
      if (localStorage.getItem('role') === 'user') {
        this.router.navigate(['/user-home']);
      } else if (localStorage.getItem('role') === 'admin') {
        this.router.navigate(['/admin-home']);
      }
      return false;
    }
  }
}
