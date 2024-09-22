import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Practitioner } from '../models/practitioner.model';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HttpPractitionerService {

  practitioner?:Practitioner;
  constructor(private httpClient: HttpClient,private router: Router) { }

  getAll(): Observable<Practitioner[]> {
    return this.httpClient.get<Practitioner[]>(`${environment.serverUrl}/app/practitioner`);
  }

  getByPage(page: number, size: number, sortBy: string='name', sortOrder: string = 'asc',gender:string='',qualification:string='') {
    return this.httpClient.get<any>(`${environment.serverUrl}/app/practitioner/filter?pageNo=${page-1}&pageSize=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}&gender=${gender}&qualification=${qualification}`);
  }
  getById(id:number){
    return this.httpClient.get<Practitioner>(`${environment.serverUrl}/app/practitioner/${id}`);
  }

  deletePractitioner(id:number){
   return this.httpClient.delete<Practitioner>(`${environment.serverUrl}/app/practitioner/${id}`,{responseType: 'text' as 'json'});
  }

  updatePractitioner(practitioner:Practitioner){
   return this.httpClient.put<Practitioner>(`${environment.serverUrl}/app/practitioner/${practitioner.id}`,practitioner);
  }

  addPractitioner(practitioner:Practitioner){
    return this.httpClient.post<Practitioner>(`${environment.serverUrl}/app/practitioner`,practitioner);
  }

}
