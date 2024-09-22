import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Organization } from '../models/organization.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PageDto } from '../models/page.dto';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root',
})
export class HttpOrganizationService {
  organization?: Organization;
  constructor(private httpClient: HttpClient, private router: Router) {}

  getAll(): Observable<Organization[]> {
    return this.httpClient.get<Organization[]>(
      `${environment.serverUrl}/app/organization`
    );
  }

  getByPage(
    page: number,
    size: number,
    sortOrder: string='asc',
    sortBy: string = '',
    type: string='',
    name: string =''
  ) {
    return this.httpClient.get<any>(
      `${environment.serverUrl}/app/organization/filter?pageNo=${
        page - 1
      }&pageSize=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}&orgType=${type}&name=${name}`
    );
  }


  getById(id: number) {
    return this.httpClient.get<Organization>(
      `${environment.serverUrl}/app/organization/${id}`
    );
  }

  deleteOrganization(id: number) {
    return this.httpClient
      .delete<Organization>(`${environment.serverUrl}/app/organization/${id}`,{responseType: 'text' as 'json'})

  }

  updateOrganization(organization: Organization) {
    return this.httpClient
      .put<Organization>(
        `${environment.serverUrl}/app/organization/${organization.id}`,
        organization
      )
  }

  addOrganization(organization: Organization) {
    return this.httpClient
      .post<Organization>(
        `${environment.serverUrl}/app/organization`,
        organization
      )
  }
}
