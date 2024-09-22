import { Component, OnInit } from '@angular/core';
import { Examination } from 'src/app/core/models/examination.model';
import { Organization } from 'src/app/core/models/organization.model';
import { Patient } from 'src/app/core/models/patient.model';
import { Practitioner } from 'src/app/core/models/practitioner.model';
import { HttpExaminationService } from 'src/app/core/services/http-examination.service';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(){

  }

  ngOnInit(): void {
  }


}
