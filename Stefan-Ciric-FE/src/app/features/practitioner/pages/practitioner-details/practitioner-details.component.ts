import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { take } from 'rxjs';
import { Gender, Qualification } from 'src/app/core/models/enums.model';
import { Organization } from 'src/app/core/models/organization.model';
import { Practitioner } from 'src/app/core/models/practitioner.model';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';

@Component({
  selector: 'app-practitioner-details',
  templateUrl: './practitioner-details.component.html',
  styleUrls: ['./practitioner-details.component.css']
})
export class PractitionerDetailsComponent implements OnInit {

  practitioner?: Practitioner;
  organization?:Organization;
  constructor(
    private activeRoute: ActivatedRoute,
    private httpPractitioner: HttpPractitionerService,
    private httpOrganization:HttpOrganizationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.activeRoute.snapshot.paramMap.get('id'));

    this.httpPractitioner.getById(id).subscribe(practPage =>{
        this.practitioner=practPage;
        this.setOrganization(this.practitioner.organizationId);
      });
  }

  getQualificationValue(s:string){
    let key = Object.keys(Qualification).indexOf(s);
    let value = Object.values(Qualification)[key];
    return value;
  }
  getGenderValue(s:string){
    let key = Object.keys(Gender).indexOf(s);
    let value = Object.values(Gender)[key];
    return value;
  }
  setOrganization(id:number){
    this.httpOrganization.getById(id).subscribe(organizationPage => {this.organization = organizationPage
    })
  }

}
