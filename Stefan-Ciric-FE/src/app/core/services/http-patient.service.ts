import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Patient } from '../models/patient.model';

@Injectable({
  providedIn: 'root',
})
export class HttpPatientService {
  patient?: Patient;

  constructor(private httpClient: HttpClient, private router: Router) {}

  getAll(): Observable<Patient[]> {
    return this.httpClient.get<Patient[]>(
      `${environment.serverUrl}/app/patient`
    );
  }

  getByPage(page: number,size: number,sortBy: string = 'name',sortOrder: string = 'asc',gender:string='',maritalStatus:string='',filterWord:string='') {
    return this.httpClient.get<any>(`${environment.serverUrl}/app/patient/filter?pageNo=${page-1}&pageSize=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}&gender=${gender}&maritalStatus=${maritalStatus}&filterWord=${filterWord}`);
  }
  getById(id: number) {
    return this.httpClient.get<Patient>(
      `${environment.serverUrl}/app/patient/${id}`
    );
  }

  deletePatient(id: number) {
    return this.httpClient
      .delete<Patient>(`${environment.serverUrl}/app/patient/${id}`,{responseType: 'text' as 'json'})

  }

  updatePatient(patient:Patient){
    return this.httpClient.put<Patient>(`${environment.serverUrl}/app/patient/${patient.id}`,patient);
  }

  addPatient(patient: Patient) {
    return this.httpClient
      .post<Patient>(`${environment.serverUrl}/app/patient`, patient)
  }
}
