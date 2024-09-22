import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Priority, Status } from 'src/app/core/models/enums.model';
import { Examination } from 'src/app/core/models/examination.model';
import { Organization } from 'src/app/core/models/organization.model';
import { Patient } from 'src/app/core/models/patient.model';
import { Practitioner } from 'src/app/core/models/practitioner.model';
import { HttpExaminationService } from 'src/app/core/services/http-examination.service';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';

@Component({
  selector: 'app-examination-details',
  templateUrl: './examination-details.component.html',
  styleUrls: ['./examination-details.component.css'],
})
export class ExaminationDetailsComponent implements OnInit {
  examination?: Examination;
  organization?: Organization;
  practitioners: Practitioner[] = [];
  patient?: Patient;

  constructor(
    private activeRoute: ActivatedRoute,
    private httpExamination: HttpExaminationService,
    private httpPractitioner: HttpPractitionerService,
    private httpPatient: HttpPatientService,
    private httpOrganization: HttpOrganizationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.activeRoute.snapshot.paramMap.get('id'));
    this.httpExamination.getById(id).subscribe((examinationPage) => {
      this.examination = examinationPage;
      this.setOrganization(this.examination.organizationId);
      this.setPatient(this.examination.patientId);
      this.loadPractitioners(this.examination);
    });
  }

  loadPractitioners(examination: Examination) {
    const practitioners = examination.practitionerIds;
    for (let id of practitioners) {
      this.httpPractitioner
        .getById(id)
        .subscribe((p) => this.practitioners.push(p));
    }
  }

  setPatient(id: number) {
    this.httpPatient.getById(id).subscribe((patientPage) => {
      this.patient = patientPage;
    });
  }

  setOrganization(id: number) {
    this.httpOrganization.getById(id).subscribe((organizationPage) => {
      this.organization = organizationPage;
    });
  }

  getStatusValue(s:string){
    let key = Object.keys(Status).indexOf(s);
    let value = Object.values(Status)[key];
    return value;
  }

  getPriorityValue(s:string){
   let key = Object.keys(Priority).indexOf(s);
    let value = Object.values(Priority)[key];
    return value;
  }



}
