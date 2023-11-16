import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import AppUser from '../model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}
  private baseURL = `http://localhost:8080/api/examease/user`;
  addUser(data: AppUser): Observable<String> {
    return this.http.post<String>(this.baseURL + '/auth/register', data);
  }
  authenticateUser(data: any) {
    const loginUser = {
      email: data.email,
      password: data.password,
    };
    return this.http.post(this.baseURL + '/auth/login', loginUser);
  }
  updateUser(data: any) {
    return this.http.patch(this.baseURL + `/users/${localStorage.getItem("id")}`, data, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
    
  }
  getUser(){
    return this.http.get(this.baseURL + `/users/${localStorage.getItem("id")}`,{
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
  }
}
