import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ServiceType } from '../models/serviceType.model';

@Injectable({
  providedIn: 'root'
})
export class HttpServiceTypeService {

  serviceType?:ServiceType;
  constructor(private httpClient: HttpClient,private router: Router) { }

  getAll(): Observable<ServiceType[]> {
    return this.httpClient.get<ServiceType[]>(`${environment.serverUrl}/app/service_type`);
  }

  getById(id:number){
    return this.httpClient.get<ServiceType>(`${environment.serverUrl}/app/service_type/${id}`);
  }

}
