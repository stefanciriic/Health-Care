import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Examination } from '../models/examination.model';
import { Practitioner } from '../models/practitioner.model';

@Injectable({
  providedIn: 'root'
})
export class HttpExaminationService {

  examination?:Examination;
  constructor(private httpClient: HttpClient,private router: Router) { }

  getAll(): Observable<Examination[]> {
    return this.httpClient.get<Examination[]>(`${environment.serverUrl}/app/examination`);
  }

  getByPage(page: number, size: number, sortBy: string, sortOrder: string = 'asc',status:string='',priority:string='') {
    return this.httpClient.get<any>(`${environment.serverUrl}/app/examination/filter?pageNo=${page-1}&pageSize=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}&status=${status}&priority=${priority}`);
  }
  getById(id:number){
    return this.httpClient.get<Examination>(`${environment.serverUrl}/app/examination/${id}`);
  }

  deleteExamination(id:number){
   return this.httpClient.delete<Examination>(`${environment.serverUrl}/app/examination/${id}`).subscribe(examination => this.router.navigate(['examination']));
  }

  updateExamination(examination:Examination){
   return this.httpClient.put<Examination>(`${environment.serverUrl}/app/examination/${examination.id}`,examination)
  }

  addExamination(examination:Examination){
    return this.httpClient.post<Examination>(`${environment.serverUrl}/app/examination`,examination)
  }

}
