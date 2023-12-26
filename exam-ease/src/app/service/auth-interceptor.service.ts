import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';

export class AuthInterceptorService implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (req.url.includes('login') || req.url.includes('register')) {
      return next.handle(req);
    }
    if (!req.headers.has('Authorization')) {
      let modifiedRequest = req.clone({
        headers: req.headers.append(
          'Authorization',
          `Bearer ${localStorage.getItem('token')}`
        ),
      });
      console.log('Inside Interceptor');
      return next.handle(modifiedRequest);
    }
    return next.handle(req);
  }
}
