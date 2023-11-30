import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Quiz } from '../model';

@Injectable({
  providedIn: 'root',
})
export class QuizService {
  // http://localhost:8080/api/examease/subcategories
  constructor(private http: HttpClient) {}
  baseURL: string = 'http://localhost:8080/api/examease';
  getCategories() {
    return this.http.get(this.baseURL + '/categories', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
  }
  addQuiz(data: Quiz) {
    return this.http.post(this.baseURL + '/quizes', data, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    });
  }
  getSubcategories(){
    return this.http.get(this.baseURL+'/subcategories', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    })
  }

  getQuizes(){
    return this.http.get(this.baseURL+'/quizes',{
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    } )
  }
  getQuizById(id:Number){
    return this.http.get(this.baseURL+`/quizes/${id}`,{
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    } )
  }
}
