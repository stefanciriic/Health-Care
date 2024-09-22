import { Injectable } from '@angular/core';
import {
  Router,
  Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
  ActivatedRoute,
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { PageDto } from 'src/app/core/models/page.dto';
import { Practitioner } from 'src/app/core/models/practitioner.model';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';

@Injectable({
  providedIn: 'root',
})
export class PractitionerListResolver
  implements Resolve<PageDto<Practitioner>>
{
  constructor(
    private httpPractitioner: HttpPractitionerService,
    private activeRoute: ActivatedRoute
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<PageDto<Practitioner>> {
    const page = Number(this.activeRoute.snapshot.queryParams['page']);
    const size = this.activeRoute.snapshot.queryParams['size']
    return this.httpPractitioner.getByPage(page ? page : 1, size? size:5,'');
  }
}
