import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserDetails } from '../models/UserDetails';

@Injectable({
  providedIn: 'root'
})
export class HttpUserService {


  constructor(private httpClient: HttpClient) { }

  login({username, password}: {username: string, password: string}): Observable<UserDetails> {
    let user={
      username:username,
      password:password
    }
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post<UserDetails>(`${environment.serverUrl}/app/auth/login`, user, { headers: headers });

  }

}
