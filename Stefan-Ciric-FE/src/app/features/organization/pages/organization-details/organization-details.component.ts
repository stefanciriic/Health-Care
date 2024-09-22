import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { take } from 'rxjs';
import { Organization } from 'src/app/core/models/organization.model';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';

@Component({
  selector: 'app-organization-details',
  templateUrl: './organization-details.component.html',
  styleUrls: ['./organization-details.component.css'],
})
export class OrganizationDetailsComponent implements OnInit {
  organization?: Organization;

  constructor(
    private activeRoute: ActivatedRoute,
    private httpOrganization: HttpOrganizationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.activeRoute.snapshot.paramMap.get('id'));
    this.httpOrganization
      .getById(id)
      .pipe(take(1))
      .subscribe((organization) => (this.organization = organization));
  }

}
