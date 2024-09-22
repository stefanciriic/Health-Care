import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Subject, takeUntil, throwError } from 'rxjs';
import { OrganizationType } from 'src/app/core/models/enums.model';
import { Organization } from 'src/app/core/models/organization.model';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-organization-form',
  templateUrl: './organization-form.component.html',
  styleUrls: ['./organization-form.component.css']
})
export class OrganizationFormComponent implements OnInit {

  organizationTypes=OrganizationType;
  @ViewChild('tempRef') message?: TemplateRef<any>;
  destroy$: Subject<boolean> = new Subject();
  organizationForm?: FormGroup;
  edit = false;
  errorMessage=''

  constructor(
    private activeRoute: ActivatedRoute,
    private httpOrganization: HttpOrganizationService,
    private fb: FormBuilder,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.prepareData();
  }


  prepareData() {
    this.edit = this.activeRoute.snapshot.data['edit'];
    const IdStr = this.activeRoute.snapshot.paramMap.get('id');
    if (this.edit && IdStr) {
      const id = Number(IdStr);
      this.loadOrganization(id);
    } else {
      this.buildForm();
    }
  }

  loadOrganization(id: number) {
    this.httpOrganization.getById(id)
    .pipe(
      takeUntil(this.destroy$)
    )
    .subscribe(ex => {
      this.buildForm(ex);
    });
  }

  buildForm(organization?:Organization) {
    this.organizationForm = this.fb.group({
      id:[organization? organization.id:0],
      identifier:[organization? organization.identifier:null ,[Validators.minLength(5)]],
      active:[organization? organization.active:true],
      type: [organization? organization.type:null,[Validators.required,Validators.minLength(5)]],
      name: [organization? organization.name:null,Validators.required],
      address: [organization? organization.address:null],
      phone: [organization? organization.phone:null,Validators.pattern('^([+]?)[0-9]{2,3}\\/?[0-9]{3}\\-?[0-9]{3,5}$')],
      email: [organization? organization.email:null,Validators.pattern('^[a-zA-Z]([a-zA-Z0-9]|\\.|\\-|\\_)*[a-zA-Z0-9]@([a-z]{2,}\\.)+([a-z]{2,})$')],
    });
  }

  saveOrganization() {
    if (this.edit) {
      return this.httpOrganization.updateOrganization(this.organizationForm?.value)
    } else {
      return this.httpOrganization.addOrganization(this.organizationForm?.value)
    }
  }

  onSubmit(tempRef: TemplateRef<any>) {
    this.saveOrganization().pipe(catchError((err) => {
      this.parseError(err); return throwError(err);})).subscribe(()=>
      this.router.navigate(['organization'])
    );
  }

  parseError(err: any){
    const str = JSON.stringify(err);
    const obj = JSON.parse(str);
    this.errorMessage = obj.error;
  }

  hasErrors(controlName: string, error: string) {
    const control = this.organizationForm?.get(controlName);
    return (
      control && control.hasError(error) && (control.touched || control.dirty)
    );
  }
}
