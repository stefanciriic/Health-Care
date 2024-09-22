import { Component, OnInit, TemplateRef, ɵɵsetComponentScope } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, of, Subject, take, takeUntil, throwError, timeout } from 'rxjs';
import { Priority, Status } from 'src/app/core/models/enums.model';
import { Examination } from 'src/app/core/models/examination.model';
import { Organization } from 'src/app/core/models/organization.model';
import { Patient } from 'src/app/core/models/patient.model';
import { Practitioner } from 'src/app/core/models/practitioner.model';
import { ServiceType } from 'src/app/core/models/serviceType.model';
import { HttpExaminationService } from 'src/app/core/services/http-examination.service';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';
import { HttpServiceTypeService } from 'src/app/core/services/http-service-type.service';

@Component({
  selector: 'app-examination-form',
  templateUrl: './examination-form.component.html',
  styleUrls: ['./examination-form.component.css']
})
export class ExaminationFormComponent implements OnInit {

  destroy$: Subject<boolean> = new Subject();
  examinationForm?: FormGroup;
  edit = false;
  organizations?: Organization[];
  practitioners?: Practitioner[];
  chosenPractiitoners:number[]=[];
  serviceTypes?:ServiceType[];
  patients?:Patient[];
  statuses=Status;
  priorities=Priority;
  patient?:Patient;
  organization?:Organization;
  errorMessage=''

  constructor(private fb: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private httpPatient: HttpPatientService,
              private httpExamination:HttpExaminationService,
              private httpPractitioner: HttpPractitionerService,
              private httpOrganization:HttpOrganizationService,
              private httpServiceType:HttpServiceTypeService) { }

    ngOnInit(): void {
    this.httpOrganization.getAll().subscribe(organizations => this.organizations=organizations);
    this.httpPractitioner.getAll().subscribe(practitioners => this.practitioners=practitioners);
    this.httpPatient.getAll().subscribe(patients => this.patients=patients);
    this.httpServiceType.getAll().subscribe(serviceTypes =>this.serviceTypes=serviceTypes )
    this.prepareData();
  }

  prepareData() {
    this.edit = this.route.snapshot.data['edit'];
    const IdStr = this.route.snapshot.paramMap.get('id')
    if (this.edit && IdStr) {
      this.httpExamination.getById(Number(IdStr)).subscribe(ex=>this.chosenPractiitoners=ex.practitionerIds)
      const id = Number(IdStr);
      this.loadExamination(id);
    } else {
      this.buildForm();
    }
  }
  loadExamination(id: number) {
    this.httpExamination.getById(id)
    .pipe(
      takeUntil(this.destroy$)
    )
    .subscribe(ex => {
      this.buildForm(ex);
    });
  }

  onCheckedPractitioner(e:any){
    if(e.target.checked){
      const id = Number(e.target.value)
      this.chosenPractiitoners.push(id);
    }else{
      this.chosenPractiitoners.forEach((id,ind) => {
          if (id == e.target.value)  {
             this.chosenPractiitoners.splice(ind,1);
             return;
            }
      })
    }
  }


  buildForm(examination?:Examination) {
    this.examinationForm = this.fb.group({
      id:[examination? examination.id:0],
      identifier:[examination? examination.identifier:null ,[Validators.minLength(5)]],
      status:[examination? examination.status:null],
      serviceType:[examination? examination.serviceType:null],
      priority:[examination? examination.priority:null],
      startDate:[examination? examination.startDate:new Date()],
      endDate:[examination? examination.endDate:new Date()],
      diagnosis:[examination? examination.diagnosis:null],
      organizationId:[examination? examination.organizationId:0],
      patientId:[examination? examination.patientId:0],
      practitionerIds:[examination? examination.practitionerIds:[]]
    });
  }

  saveExamination() {
    if (this.edit) {
      var form = this.examinationForm?.getRawValue();
      form.practitionerIds=this.chosenPractiitoners;
      return this.httpExamination.updateExamination(form)
    } else {
      var form = this.examinationForm?.getRawValue();
      form.practitionerIds=this.chosenPractiitoners;
      return this.httpExamination.addExamination(form);
    }

  }

  onSubmit(tempRef: TemplateRef<any>) {
    this.saveExamination().pipe(catchError((err) => {
      this.parseError(err); return throwError(err);})).subscribe(()=>
      this.router.navigate(['examination'])
    );
  }

  parseError(err: any){
    const str = JSON.stringify(err);
    const obj = JSON.parse(str);
    this.errorMessage = obj.error;
  }

  hasErrors(componentName: string, errorCode?: string) {
    return  (this.examinationForm?.get(componentName)?.dirty || this.examinationForm?.get(componentName)?.touched) &&
    ((!errorCode && this.examinationForm?.get(componentName)?.errors ) ||
    (errorCode && this.examinationForm?.get(componentName)?.hasError(errorCode)));
  }

  ngOnDestroy() {
    this.destroy$.next(true);
  }

}
