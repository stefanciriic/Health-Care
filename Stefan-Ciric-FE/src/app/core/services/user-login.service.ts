import { Injectable } from '@angular/core';
import { BehaviorSubject ,Subject} from 'rxjs';
import { UserDetails } from '../models/UserDetails';

@Injectable({
  providedIn: 'root'
})
export class UserLoginService {

  constructor() {
    const userDataString = this.storage.getItem('userData');
  }

  get storage() {
    return localStorage;
  }

  setLoginCredentials(userDetails: UserDetails) {
    this.storage.setItem('token', 'Basic ' + btoa(`${userDetails.username}:${userDetails.password}`))
  }

  logoutUser() {
    this.storage.removeItem('token');
  }

  get token() {
    return this.storage.getItem('token');
  }

  get isUserLoggedIn() {
    return !!this.token;
  }

}
