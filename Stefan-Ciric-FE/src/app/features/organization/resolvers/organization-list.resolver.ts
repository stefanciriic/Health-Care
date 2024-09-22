import { Injectable } from '@angular/core';
import {
  Router,
  Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
  ActivatedRoute,
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Organization } from 'src/app/core/models/organization.model';
import { PageDto } from 'src/app/core/models/page.dto';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';

@Injectable({
  providedIn: 'root',
})
export class OrganizationListResolver implements Resolve<PageDto<Organization>> {

  constructor(
    private httpOrganization: HttpOrganizationService,
    private activeRoute: ActivatedRoute
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<PageDto<Organization>> {

    const page = Number(this.activeRoute.snapshot.queryParams['page']);
    const size = this.activeRoute.snapshot.queryParams['size']

    return this.httpOrganization.getByPage(page ? page : 1, size? size:5,'');

  }
}
