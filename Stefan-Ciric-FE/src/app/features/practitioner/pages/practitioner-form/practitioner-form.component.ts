import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Subject, takeUntil, throwError } from 'rxjs';
import { Gender, Qualification } from 'src/app/core/models/enums.model';
import { Organization } from 'src/app/core/models/organization.model';
import { Practitioner } from 'src/app/core/models/practitioner.model';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';

@Component({
  selector: 'app-practitioner-form',
  templateUrl: './practitioner-form.component.html',
  styleUrls: ['./practitioner-form.component.css']
})
export class PractitionerFormComponent implements OnInit {

  @ViewChild('tempRef') message?: TemplateRef<any>;
  destroy$: Subject<boolean> = new Subject();
  practitionerForm?: FormGroup;
  edit = false;
  errorMessage=''
  organizations?:Organization[];
  genders=Gender;
  qualifications=Qualification;

   constructor(
    private activeRoute: ActivatedRoute,
    private httpPractitioner: HttpPractitionerService,
    private httpOrganization:HttpOrganizationService,
    private fb: FormBuilder,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.httpOrganization.getAll().subscribe(organizations=>this.organizations=organizations)
    this.prepareData();
  }


  prepareData() {
    this.edit = this.activeRoute.snapshot.data['edit'];
    const IdStr = this.activeRoute.snapshot.paramMap.get('id');
    if (this.edit && IdStr) {
      const id = Number(IdStr);
      this.loadPatient(id);
    } else {
      this.buildForm();
    }
  }

  loadPatient(id: number) {
    this.httpPractitioner.getById(id)
    .pipe(
      takeUntil(this.destroy$)
    )
    .subscribe(ex => {
      this.buildForm(ex);
    });
  }

  buildForm(practitioner?:Practitioner) {
    this.practitionerForm = this.fb.group({
      id:[practitioner? practitioner.id:0],
      identifier:[practitioner? practitioner.identifier:null ,[Validators.minLength(5)]],
      active:[practitioner? practitioner.active:true],
      name: [practitioner? practitioner.name:null,Validators.required],
      surname: [practitioner? practitioner.surname:null,Validators.required],
      gender:[practitioner? practitioner.gender:null],
      birthDate:[practitioner? practitioner.birthDate:new Date()],
      address: [practitioner? practitioner.address:null],
      phone: [practitioner? practitioner.phone:null,Validators.pattern('^([+]?)[0-9]{2,3}\\/?[0-9]{3}\\-?[0-9]{3,5}$')],
      email: [practitioner? practitioner.email:null,Validators.pattern('^[a-zA-Z]([a-zA-Z0-9]|\\.|\\-|\\_)*[a-zA-Z0-9]@([a-z]{2,}\\.)+([a-z]{2,})$')],
      qualification:[practitioner? practitioner.qualification:null],
      organizationId:[practitioner? practitioner.organizationId:0],
    });
  }

  savePractitioner() {
    if (this.edit) {
      return this.httpPractitioner.updatePractitioner(this.practitionerForm?.value)
    } else {
      return this.httpPractitioner.addPractitioner(this.practitionerForm?.value)
    }
  }

  onSubmit(tempRef: TemplateRef<any>) {
    this.savePractitioner().pipe(catchError((err) => {
      this.parseError(err); return throwError(err);})).subscribe(()=>
      this.router.navigate(['practitioner'])
    );
  }

  parseError(err: any){
    const str = JSON.stringify(err);
    const obj = JSON.parse(str);
    this.errorMessage = obj.error;
  }

  hasErrors(controlName: string, error: string) {
    const control = this.practitionerForm?.get(controlName);
    return (
      control && control.hasError(error) && (control.touched || control.dirty)
    );
  }


}
