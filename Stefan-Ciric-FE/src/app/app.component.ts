import { Component } from '@angular/core';
import { UserLoginService } from './core/services/user-login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Healht Care Project';

  constructor() {
  }
  ngOnInit() {
  }
}
