import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserLoginService } from 'src/app/core/services/user-login.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor(private userLoginService: UserLoginService, private router: Router) { }

  ngOnInit(): void {
  }
  logoutUser() {
    this.userLoginService.logoutUser();
    this.router.navigate(["/login"]);
  }

}
