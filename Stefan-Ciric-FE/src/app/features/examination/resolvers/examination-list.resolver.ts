import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
  ActivatedRoute
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Examination } from 'src/app/core/models/examination.model';
import { PageDto } from 'src/app/core/models/page.dto';
import { HttpExaminationService } from 'src/app/core/services/http-examination.service';

@Injectable({
  providedIn: 'root'
})
export class ExaminationListResolver implements Resolve<PageDto<Examination>> {
  constructor(
    private httpExamination: HttpExaminationService,
    private activeRoute: ActivatedRoute
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<PageDto<Examination>> {
    const page = Number(this.activeRoute.snapshot.queryParams['page']);
    const size = this.activeRoute.snapshot.queryParams['size']
    return this.httpExamination.getByPage(page ? page : 1,size? size:5,'');
  }
}
