import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { take } from 'rxjs';
import { Gender, MaritalStatus } from 'src/app/core/models/enums.model';
import { Organization } from 'src/app/core/models/organization.model';
import { Patient } from 'src/app/core/models/patient.model';
import { Practitioner } from 'src/app/core/models/practitioner.model';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css']
})
export class PatientDetailsComponent implements OnInit {

  patient?: Patient;
  generalPractitioner?:Practitioner;
  organization?:Organization;


  constructor(
    private activeRoute: ActivatedRoute,
    private httpPatient: HttpPatientService,
    private httpPractitioner:HttpPractitionerService,
    private httpOrganization:HttpOrganizationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.activeRoute.snapshot.paramMap.get('id'));
    this.httpPatient.getById(id).subscribe(patientPage =>{
        this.patient=patientPage;
        this.setPractitoner(this.patient.generalPractitionerId);
        this.setOrganization(this.patient.organizationId);
      });
  }

  setPractitoner(id:number){
    this.httpPractitioner.getById(id).subscribe(practitionerPage => {this.generalPractitioner = practitionerPage
    })

  }
  setOrganization(id:number){
    this.httpOrganization.getById(id).subscribe(organizationPage => {this.organization = organizationPage
    })
  }
  getGenderValue(s:string){
    let key = Object.keys(Gender).indexOf(s);
    let value = Object.values(Gender)[key];
    return value;
  }

  getMaritalStatusValue(s:string){
    let key = Object.keys(MaritalStatus).indexOf(s);
    let value = Object.values(MaritalStatus)[key];
    return value;
  }

}
