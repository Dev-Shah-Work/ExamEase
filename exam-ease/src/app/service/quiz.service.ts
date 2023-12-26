import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Quiz, Test } from '../model';

@Injectable({
  providedIn: 'root',
})
export class QuizService {
  // http://localhost:8080/api/examease/subcategories
  constructor(private http: HttpClient) {}
  baseURL: string = 'http://localhost:8080/api/examease';
  getCategories() {
    return this.http.get(this.baseURL + '/categories');
  }
  addQuiz(data: Quiz) {
    return this.http.post(this.baseURL + '/quizes', data);
  }
  getSubcategories() {
    return this.http.get(this.baseURL + '/subcategories');
  }

  getQuizes() {
    return this.http.get(this.baseURL + '/quizes');
  }
  getQuizesByPagination(
    pageSize: Number,
    pageNumber: Number,
    subcategoryId?: Number
  ) {
    if (subcategoryId != undefined || subcategoryId != null) {
      return this.http.get(
        this.baseURL +
          `/quizes-pagination?pageNumber=${pageNumber}&pageSize=${pageSize}&subcategoryId=${subcategoryId}`
      );
    }
    return this.http.get(
      this.baseURL +
        `/quizes-pagination?pageNumber=${pageNumber}&pageSize=${pageSize}`
    );
  }
  getQuizById(id: Number) {
    return this.http.get(this.baseURL + `/quizes/${id}`);
  }
  addTest(data: Test) {
    return this.http.post(this.baseURL + `/tests`, data);
  }
  getPortalStats() {
    return this.http.get(this.baseURL + '/portal-stats');
  }
  getQuizCountBySubcategoryId(subId:Number){
    return this.http.get(this.baseURL+`/quizes/subcategory/count/${subId}`)
  }
}
