import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, Subscription, throwError } from 'rxjs';
import { UserDetails } from 'src/app/core/models/UserDetails';
import { HttpUserService } from 'src/app/core/services/http-user.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { UserLoginService } from 'src/app/core/services/user-login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm?: FormGroup;
  subscriptions: Subscription = new Subscription();
  errorMessage=''
  constructor(
    private fb: FormBuilder,
    private httpUser: HttpUserService,
    private router: Router,
    private userLogin: UserLoginService,
    private toastService: ToastService,
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  ngOnDestroy(): void {
      this.subscriptions.unsubscribe();
  }

  createForm() {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }


    login() {
      this.subscriptions.add(
        this.httpUser.login(this.loginForm?.value).pipe(catchError((err) => {
          this.parseError(err); return throwError(err);})).subscribe(
          (userDetais: UserDetails) => {
            this.userLogin.setLoginCredentials(userDetais);
            this.toastService.showToast('Successfull login!')
            this.router.navigate(['/home']);
          }
        )
      );
    }

    parseError(err: any){
      const str = JSON.stringify(err);
        const obj = JSON.parse(str);
        this.errorMessage = obj.error;
    }
}
