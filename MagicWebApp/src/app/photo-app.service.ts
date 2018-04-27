import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/Observable' ;
import { Photo } from "./model/Photo";

const httpOptions = {
   headers: new HttpHeaders({ 'Content-Type':'application/json'})
};


@Injectable()
export class AppService {
 
  private apiUrl = "http://localhost:8080/api/v1/photos";

  //constructor(private http : HttpClient) {}
  constructor(private http: HttpClient) {}

   findAll(): Observable<Photo []>{
    return this.http.get(this.apiUrl)
    .map((res: Response) => res.json())
    .catch((error: any) => Observable.throw(error.json().error ||  'Server error'));

  }


}
