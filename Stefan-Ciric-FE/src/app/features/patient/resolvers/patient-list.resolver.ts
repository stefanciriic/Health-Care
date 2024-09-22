import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
  ActivatedRoute
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { PageDto } from 'src/app/core/models/page.dto';
import { Patient } from 'src/app/core/models/patient.model';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';

@Injectable({
  providedIn: 'root'
})
export class PatientListResolver implements Resolve<PageDto<Patient>> {

  constructor(
    private httpPatient: HttpPatientService,
    private activeRoute: ActivatedRoute
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<PageDto<Patient>> {
    const page = Number(this.activeRoute.snapshot.queryParams['page']);
    const size = this.activeRoute.snapshot.queryParams['size']
    return this.httpPatient.getByPage(page ? page : 1, size? size:5,'');
  }
}
