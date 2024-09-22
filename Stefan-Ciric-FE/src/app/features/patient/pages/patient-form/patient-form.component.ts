import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Subject, takeUntil, throwError } from 'rxjs';
import { Gender, MaritalStatus } from 'src/app/core/models/enums.model';
import { Organization } from 'src/app/core/models/organization.model';
import { Patient } from 'src/app/core/models/patient.model';
import { Practitioner } from 'src/app/core/models/practitioner.model';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.css']
})
export class PatientFormComponent implements OnInit {

  @ViewChild('tempRef') message?: TemplateRef<any>;
  destroy$: Subject<boolean> = new Subject();
  patientForm?: FormGroup;
  edit = false;
  errorMessage=''
  organizations?:Organization[];
  practitioners?:Practitioner[];
  maritalStatuses=MaritalStatus;
  genders=Gender;


   constructor(
    private activeRoute: ActivatedRoute,
    private httpPatient: HttpPatientService,
    private httpOrganization:HttpOrganizationService,
    private httpPractitioner:HttpPractitionerService,
    private fb: FormBuilder,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.httpOrganization.getAll().subscribe(organizations=>this.organizations=organizations)
    this.httpPractitioner.getAll().subscribe(practitioners=>this.practitioners=practitioners)
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
    this.httpPatient.getById(id)
    .pipe(
      takeUntil(this.destroy$)
    )
    .subscribe(ex => {
      this.buildForm(ex);
    });
  }

  buildForm(patient?:Patient) {
    this.patientForm = this.fb.group({
      id:[patient? patient.id:0],
      identifier:[patient? patient.identifier:null ,[Validators.minLength(5)]],
      active:[patient? patient.active:true],
      name: [patient? patient.name:null,Validators.required],
      surname: [patient? patient.surname:null,Validators.required],
      gender:[patient? patient.gender:null],
      birthDate:[patient? patient.birthDate:new Date()],
      address: [patient? patient.address:null],
      phone: [patient? patient.phone:null,Validators.pattern('^([+]?)[0-9]{2,3}\\/?[0-9]{3}\\-?[0-9]{3,5}$')],
      email: [patient? patient.email:null,Validators.pattern('^[a-zA-Z]([a-zA-Z0-9]|\\.|\\-|\\_)*[a-zA-Z0-9]@([a-z]{2,}\\.)+([a-z]{2,})$')],
      deceased:[patient? patient.deceased:true],
      maritalStatus:[patient? patient.maritalStatus:null],
      organizationId:[patient? patient.organizationId:0],
      generalPractitionerId:[patient? patient.generalPractitionerId:0]
    });
  }

  savePatient() {
    if (this.edit) {
      return this.httpPatient.updatePatient(this.patientForm?.value)
    } else {
      return this.httpPatient.addPatient(this.patientForm?.value)
    }
  }

  onSubmit(tempRef: TemplateRef<any>) {
    this.savePatient().pipe(catchError((err) => {
      this.parseError(err); return throwError(err);})).subscribe(()=>
      this.router.navigate(['patient'])
    );
  }

  parseError(err: any){
    const str = JSON.stringify(err);
    const obj = JSON.parse(str);
    this.errorMessage = obj.error;
  }

  hasErrors(controlName: string, error: string) {
    const control = this.patientForm?.get(controlName);
    return (
      control && control.hasError(error) && (control.touched || control.dirty)
    );
  }



}
